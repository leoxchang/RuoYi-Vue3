package com.truntao.web.controller.system;

import cn.hutool.core.codec.Base64;
import com.truntao.common.constant.Constants;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.exception.file.InvalidExtensionException;
import com.truntao.common.utils.DateUtils;
import com.truntao.system.domain.dto.AvatarDTO;
import com.truntao.system.domain.dto.ProfileDTO;
import com.truntao.system.domain.ro.SysPasswordParam;
import com.truntao.system.domain.ro.SysUserUpdateParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.truntao.common.annotation.Log;
import com.truntao.common.config.TruntaoConfig;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.common.utils.file.FileUploadUtils;
import com.truntao.common.utils.file.MimeTypeUtils;
import com.truntao.framework.web.service.TokenService;
import com.truntao.system.service.ISysUserService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * 个人信息 业务处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Resource
    private ISysUserService userService;

    @Resource
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public R<ProfileDTO> profile() {
        LoginUser loginUser = getLoginUser();
        SysUserDTO user = loginUser.getUser();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUser(user);
        profileDTO.setRoleGroup(userService.selectUserRoleGroup(loginUser.getUsername()));
        profileDTO.setPostGroup(userService.selectUserPostGroup(loginUser.getUsername()));
        return R.ok(profileDTO);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> updateProfile(@RequestBody SysUserUpdateParam userUpdateParam) {
        LoginUser loginUser = getLoginUser();
        SysUserDTO sysUser = loginUser.getUser();
        userUpdateParam.setUserId(sysUser.getUserId());
        userUpdateParam.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(userUpdateParam.getPhoneNumber()) && !userService.checkPhoneUnique(userUpdateParam.getUserId(), userUpdateParam.getPhoneNumber())) {
            return R.fail("修改用户'" + userUpdateParam.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(userUpdateParam.getEmail()) && !userService.checkEmailUnique(userUpdateParam.getUserId(), userUpdateParam.getEmail())) {
            return R.fail("修改用户'" + userUpdateParam.getUserName() + "'失败，邮箱账号已存在");
        }
        userUpdateParam.setPassword(null);
        userUpdateParam.setAvatar(null);
        userUpdateParam.setDeptId(null);
        if (userService.updateUserProfile(userUpdateParam) > 0) {
            // 更新缓存用户信息
            sysUser.setNickName(userUpdateParam.getNickName());
            sysUser.setPhoneNumber(userUpdateParam.getPhoneNumber());
            sysUser.setEmail(userUpdateParam.getEmail());
            sysUser.setSex(userUpdateParam.getSex());
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R<Void> updatePwd(@RequestBody SysPasswordParam sysPasswordParam) {
        String oldPassword = sysPasswordParam.getOldPassword();
        String newPassword = sysPasswordParam.getNewPassword();
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return R.fail("新密码不能与旧密码相同");
        }
        newPassword = SecurityUtils.encryptPassword(newPassword);
        if (userService.resetUserPwd(userName, newPassword) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPwdUpdateDate(DateUtils.getNowDate());
            loginUser.getUser().setPassword(newPassword);
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public R<AvatarDTO> avatar(@RequestParam("avatarFile") MultipartFile file) throws IOException, InvalidExtensionException {
        if (!file.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(TruntaoConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AvatarDTO avatarDTO = new AvatarDTO();
                // 更新缓存用户头像
                File avatarFile =
                        FileUploadUtils.getAbsoluteFile(StringUtils.substringAfter(avatar,
                                Constants.RESOURCE_PREFIX));
                if (avatarFile.exists()) {
                    String img = Base64.encode(avatarFile);
                    loginUser.getUser().setAvatar("data:image/png;base64," + img);
                    avatarDTO.setImgUrl("data:image/png;base64," + img);
                    tokenService.setLoginUser(loginUser);
                }
                return R.ok(avatarDTO);
            }
        }
        return R.fail("上传图片异常，请联系管理员");
    }
}
