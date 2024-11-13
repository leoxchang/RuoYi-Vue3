package com.truntao.web.controller.system;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.TreeSelect;
import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.core.page.PageDTO;
import com.truntao.system.domain.dto.SysUserAuthRoleDTO;
import com.truntao.system.domain.dto.SysUserInfoDTO;
import com.truntao.system.domain.ro.SysDeptParam;
import com.truntao.system.domain.ro.SysUserParam;
import com.truntao.system.domain.ro.SysUserUpdateParam;
import com.truntao.system.service.impl.AdminService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.entity.SysUser;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.service.ISysDeptService;
import com.truntao.system.service.ISysPostService;
import com.truntao.system.service.ISysRoleService;
import com.truntao.system.service.ISysUserService;

/**
 * 用户信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private ISysUserService userService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysPostService postService;

    @Resource
    private AdminService adminService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    @GetMapping("/list")
    public R<PageDTO<SysUserDTO>> list(SysUserParam userParam) {
        startPage();
        Page<SysUserDTO> list = userService.selectUserList(userParam.getSysUser());
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserParam userParam) {
        List<SysUserDTO> list = userService.selectUserList(userParam.getSysUser());
        ExcelUtil<SysUserDTO> util = new ExcelUtil<>(SysUserDTO.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    @PostMapping("/importData")
    public R<String> importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return R.ok(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public R<SysUserInfoDTO> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        SysUserInfoDTO userInfoDTO = new SysUserInfoDTO();
        if (Objects.nonNull(userId)) {
            userService.checkUserDataScope(userId);
            SysUserDTO sysUser = userService.selectUserById(userId);
            userInfoDTO.setUser(sysUser);
            userInfoDTO.setPostIds(postService.selectPostListByUserId(userId));
            userInfoDTO.setRoleIds(sysUser.getRoles().stream().map(SysRoleDTO::getRoleId).toList());
        }

        List<SysRoleDTO> roles = roleService.selectRoleAll();
        userInfoDTO.setRoles(adminService.isAdmin(userId) ? roles :
                roles.stream().filter(r -> !roleService.isAdmin(r.getRoleId())).toList());
        userInfoDTO.setPosts(postService.selectPostAll());
        return R.ok(userInfoDTO);
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermission('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysUserParam userParam) {
        deptService.checkDeptDataScope(userParam.getDeptId());
        roleService.checkRoleDataScope(userParam.getRoleIds());
        Optional<R<Integer>> checkResult = checkUser(null, userParam.getUserName(), userParam.getPhoneNumber(),
                userParam.getEmail());
        return checkResult.orElseGet(() -> R.ok(userService.insertUser(userParam)));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysUserUpdateParam userUpdateParam) {
        userService.checkUserAllowed(userUpdateParam.getUserId());
        userService.checkUserDataScope(userUpdateParam.getUserId());
        deptService.checkDeptDataScope(userUpdateParam.getDeptId());
        roleService.checkRoleDataScope(userUpdateParam.getRoleIds());
        Optional<R<Integer>> checkResult = checkUser(userUpdateParam.getUserId(), userUpdateParam.getUserName(),
                userUpdateParam.getPhoneNumber(), userUpdateParam.getEmail());
        return checkResult.orElseGet(() -> R.ok(userService.updateUser(userUpdateParam)));

    }

    private Optional<R<Integer>> checkUser(Long userId, String userName, String phone, String email) {
        String errorMsg = "修改用户'";
        if (!userService.checkUserNameUnique(userId, userName)) {
            return Optional.of(R.fail(errorMsg + userName + "'失败，登录账号已存在"));
        } else if (StringUtils.isNotEmpty(phone) && !userService.checkPhoneUnique(userId, phone)) {
            return Optional.of(R.fail(errorMsg + userName + "'失败，手机号码已存在"));
        } else if (StringUtils.isNotEmpty(email) && !userService.checkEmailUnique(userId, email)) {
            return Optional.of(R.fail(errorMsg + userName + "'失败，邮箱账号已存在"));
        }
        return Optional.empty();
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermission('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Integer> remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.fail("当前用户不能删除");
        }
        return R.ok(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermission('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public R<Integer> resetPwd(@RequestBody SysUserUpdateParam userUpdateParam) {
        userService.checkUserAllowed(userUpdateParam.getUserId());
        userService.checkUserDataScope(userUpdateParam.getUserId());
        return R.ok(userService.resetPwd(userUpdateParam));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@RequestBody SysUserUpdateParam userUpdateParam) {
        userService.checkUserAllowed(userUpdateParam.getUserId());
        userService.checkUserDataScope(userUpdateParam.getUserId());
        return R.ok(userService.updateUserProfile(userUpdateParam));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public R<SysUserAuthRoleDTO> authRole(@PathVariable("userId") Long userId) {
        SysUserAuthRoleDTO sysUserAuthRoleDTO = new SysUserAuthRoleDTO();
        SysUserDTO user = userService.selectUserById(userId);
        List<SysRoleDTO> roles = roleService.selectRolesByUserId(userId);
        sysUserAuthRoleDTO.setUser(user);
        sysUserAuthRoleDTO.setRoles(adminService.isAdmin(userId) ? roles :
                roles.stream().filter(r -> !roleService.isAdmin(r.getRoleId())).toList());
        return R.ok(sysUserAuthRoleDTO);
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public R<Void> insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        roleService.checkRoleDataScope(roleIds);
        userService.insertUserAuth(userId, roleIds);
        return R.ok();
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    @GetMapping("/deptTree")
    public R<List<TreeSelect>> deptTree(SysDeptParam deptParam) {
        return R.ok(deptService.selectDeptTreeList(deptParam));
    }
}
