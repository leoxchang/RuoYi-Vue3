package com.truntao.framework.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.common.core.domain.dto.SysUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.truntao.system.service.ISysMenuService;
import com.truntao.system.service.ISysRoleService;

/**
 * 用户权限处理
 *
 * @author truntao
 */
@Component
public class SysPermissionService {
    private final ISysRoleService roleService;

    private final ISysMenuService menuService;
    @Autowired
    public SysPermissionService(ISysRoleService roleService,ISysMenuService menuService){
        this.roleService = roleService;
        this.menuService = menuService;
    }

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUserDTO user) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUserDTO user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRoleDTO> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRoleDTO role : roles) {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
