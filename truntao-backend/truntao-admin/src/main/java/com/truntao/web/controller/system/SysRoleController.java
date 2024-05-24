package com.truntao.web.controller.system;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.core.page.PageDTO;
import com.truntao.system.domain.dto.DeptTreeDTO;
import com.truntao.system.domain.ro.SysDeptParam;
import com.truntao.system.domain.ro.SysRoleParam;
import com.truntao.system.domain.ro.SysRoleUpdateParam;
import com.truntao.system.domain.ro.SysUserParam;
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
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.model.LoginUser;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.framework.web.service.SysPermissionService;
import com.truntao.framework.web.service.TokenService;
import com.truntao.system.domain.po.SysUserRole;
import com.truntao.system.service.ISysDeptService;
import com.truntao.system.service.ISysRoleService;
import com.truntao.system.service.ISysUserService;

/**
 * 角色信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    @Resource
    private ISysRoleService roleService;

    @Resource
    private TokenService tokenService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @PreAuthorize("@ss.hasPermission('system:role:list')")
    @GetMapping("/list")
    public R<PageDTO<SysRoleDTO>> list(SysRoleParam roleParam) {
        startPage();
        Page<SysRoleDTO> list = roleService.selectRoleList(roleParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:role:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRoleParam roleParam) {
        List<SysRoleDTO> list = roleService.selectRoleList(roleParam);
        ExcelUtil<SysRoleDTO> util = new ExcelUtil<>(SysRoleDTO.class);
        util.exportExcel(response, list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public R<SysRoleDTO> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return R.ok(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@ss.hasPermission('system:role:add')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysRoleParam roleParam) {
        if (!roleService.checkRoleNameUnique(null, roleParam.getRoleName())) {
            return R.fail("新增角色'" + roleParam.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(null, roleParam.getRoleKey())) {
            return R.fail("新增角色'" + roleParam.getRoleName() + "'失败，角色权限已存在");
        }
        return R.ok(roleService.insertRole(roleParam));

    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysRoleUpdateParam roleUpdateParam) {
        roleService.checkRoleAllowed(roleUpdateParam.getRoleId());
        roleService.checkRoleDataScope(roleUpdateParam.getRoleId());
        String errorMsg = "修改角色'";
        if (!roleService.checkRoleNameUnique(roleUpdateParam.getRoleId(), roleUpdateParam.getRoleName())) {
            return R.fail(errorMsg + roleUpdateParam.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(roleUpdateParam.getRoleId(), roleUpdateParam.getRoleKey())) {
            return R.fail(errorMsg + roleUpdateParam.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(roleUpdateParam) > 0) {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (Objects.nonNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return R.ok();
        }
        return R.fail("修改角色'" + roleUpdateParam.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public R<Integer> dataScope(@RequestBody SysRoleUpdateParam roleUpdateParam) {
        roleService.checkRoleAllowed(roleUpdateParam.getRoleId());
        roleService.checkRoleDataScope(roleUpdateParam.getRoleId());
        return R.ok(roleService.authDataScope(roleUpdateParam));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@RequestBody SysRoleUpdateParam roleUpdateParam) {
        roleService.checkRoleAllowed(roleUpdateParam.getRoleId());
        roleService.checkRoleDataScope(roleUpdateParam.getRoleId());
        return R.ok(roleService.updateRoleStatus(roleUpdateParam));
    }

    /**
     * 删除角色
     */
    @PreAuthorize("@ss.hasPermission('system:role:remove')")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public R<Integer> remove(@PathVariable Long[] roleIds) {
        return R.ok(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    @GetMapping("/option-select")
    public R<List<SysRoleDTO>> optionSelect() {
        return R.ok(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @PreAuthorize("@ss.hasPermission('system:role:list')")
    @GetMapping("/authUser/allocatedList")
    public R<List<SysUserDTO>> allocatedList(SysUserParam userParam) {
        startPage();
        List<SysUserDTO> list = userService.selectAllocatedList(userParam);
        return R.ok(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @PreAuthorize("@ss.hasPermission('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public R<List<SysUserDTO>> unallocatedList(SysUserParam userParam) {
        startPage();
        List<SysUserDTO> list = userService.selectUnallocatedList(userParam);
        return R.ok(list);
    }

    /**
     * 取消授权用户
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public R<Integer> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return R.ok(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public R<Integer> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return R.ok(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public R<Integer> selectAuthUserAll(Long roleId, Long[] userIds) {
        roleService.checkRoleDataScope(roleId);
        return R.ok(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * 获取对应角色部门树列表
     */
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    @GetMapping(value = "/deptTree/{roleId}")
    public R<DeptTreeDTO> deptTree(@PathVariable("roleId") Long roleId) {
        DeptTreeDTO deptTreeDTO = new DeptTreeDTO();
        deptTreeDTO.setCheckedKeys(deptService.selectDeptListByRoleId(roleId));
        deptTreeDTO.setDepts(deptService.selectDeptTreeList(new SysDeptParam()));
        return R.ok(deptTreeDTO);
    }
}
