package com.truntao.web.controller.system;

import cn.hutool.core.codec.Base64;
import com.truntao.common.constant.Constants;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysMenuDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.core.domain.model.LoginBody;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.core.text.Convert;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.common.utils.file.FileUploadUtils;
import com.truntao.framework.web.service.SysLoginService;
import com.truntao.framework.web.service.SysPermissionService;
import com.truntao.framework.web.service.TokenService;
import com.truntao.system.domain.dto.LoginDTO;
import com.truntao.system.domain.dto.LoginUserInfoDTO;
import com.truntao.system.domain.vo.RouterVo;
import com.truntao.system.service.ISysConfigService;
import com.truntao.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 登录验证
 *
 * @author truntao
 */
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private TokenService tokenService;

    @Resource
    private ISysConfigService configService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public R<LoginDTO> login(@RequestBody LoginBody loginBody) {
        LoginDTO loginDTO = new LoginDTO();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        loginDTO.setToken(token);
        return R.ok(loginDTO);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<LoginUserInfoDTO> getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUserDTO user = loginUser.getUser();
        if (StringUtils.isNotEmpty(user.getAvatar()) && !user.getAvatar().contains("data:image/png;base64,")) {
            File avatarFile =
                    FileUploadUtils.getAbsoluteFile(StringUtils.substringAfter(user.getAvatar(),
                            Constants.RESOURCE_PREFIX));
            if (avatarFile.exists()) {
                String img = Base64.encode(avatarFile);
                user.setAvatar("data:image/png;base64," + img);
            }
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        LoginUserInfoDTO loginUserInfoDTO = new LoginUserInfoDTO();
        loginUserInfoDTO.setUser(user);
        loginUserInfoDTO.setRoles(roles);
        loginUserInfoDTO.setPermissions(permissions);
        loginUserInfoDTO.setDefaultModifyPwd(initPasswordIsModify(user.getPwdUpdateDate()));
        return R.ok(loginUserInfoDTO);
    }

    // 检查初始密码是否提醒修改
    private  boolean initPasswordIsModify(Date pwdUpdateDate) {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenuDTO> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }
}
