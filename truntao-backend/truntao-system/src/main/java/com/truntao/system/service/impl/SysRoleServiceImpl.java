package com.truntao.system.service.impl;

import java.util.*;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.system.domain.ro.SysRoleParam;
import com.truntao.system.domain.ro.SysRoleUpdateParam;
import com.truntao.system.service.ISysRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.truntao.common.annotation.DataScope;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.domain.entity.SysRole;
import com.truntao.common.exception.ServiceException;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.common.utils.spring.SpringUtils;
import com.truntao.system.domain.po.SysRoleDept;
import com.truntao.system.domain.po.SysRoleMenu;
import com.truntao.system.domain.po.SysUserRole;
import com.truntao.system.mapper.SysRoleDeptMapper;
import com.truntao.system.mapper.SysRoleMapper;
import com.truntao.system.mapper.SysRoleMenuMapper;
import com.truntao.system.mapper.SysUserRoleMapper;

import javax.annotation.Resource;

/**
 * 角色 业务层处理
 *
 * @author truntao
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysRoleDeptMapper roleDeptMapper;
    @Resource
    private AdminService adminService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param roleParam 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public Page<SysRoleDTO> selectRoleList(SysRoleParam roleParam) {
        SysRole role = roleParam.getSysRole();
        Page<SysRoleDTO> page = new Page<>();
        try (Page<SysRole> list = roleMapper.selectRoleList(role)) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysRoleDTO::new).toList());
        }
        return page;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRoleDTO> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRoleDTO> roles = selectRoleAll();
        for (SysRoleDTO role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (Objects.nonNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRoleDTO> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRoleParam());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRoleDTO selectRoleById(Long roleId) {
        return new SysRoleDTO(roleMapper.selectRoleById(roleId));
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param roleId 角色Id
     * @param roleName 角色名称
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(Long roleId,String roleName) {
        SysRole info = roleMapper.checkRoleNameUnique(roleName);
        if (Objects.nonNull(info) && !Objects.equals(info.getRoleId(),roleId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param roleId 角色Id
     * @param roleKey 角色key
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(Long roleId,String roleKey) {
        SysRole info = roleMapper.checkRoleKeyUnique(roleKey);
        if (Objects.nonNull(info) && !Objects.equals(info.getRoleId(),roleId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param roleId 角色信息
     */
    @Override
    public void checkRoleAllowed(Long roleId) {
        if (Objects.nonNull(roleId) && isAdmin(roleId)) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    @Override
    public boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleIds 角色ids
     */
    @Override
    public void checkRoleDataScope(Long... roleIds) {
        if (!adminService.isAdmin(SecurityUtils.getUserId())) {
            for (Long roleId : roleIds) {
                SysRole role = new SysRole();
                role.setRoleId(roleId);
                List<SysRole> roles = roleMapper.selectRoleList(role);
                if (CollectionUtils.isEmpty(roles)) {
                    throw new ServiceException("没有权限访问角色数据！");
                }
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param roleParam 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRoleParam roleParam) {
        SysRole role = roleParam.getSysRole();
        role.setCreateBy(SecurityUtils.getUsername());
        role.setCreateTime(new Date());
        // 新增角色信息
        roleMapper.insert(role);
        return insertRoleMenu(role.getRoleId(), roleParam.getMenuIds());
    }

    /**
     * 修改保存角色信息
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRoleUpdateParam roleUpdateParam) {
        SysRole role = roleUpdateParam.getSysRole();
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(new Date());
        // 修改角色信息
        roleMapper.updateById(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role.getRoleId(), roleUpdateParam.getMenuIds());
    }

    /**
     * 修改角色状态
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRoleUpdateParam roleUpdateParam) {
        SysRole role = roleUpdateParam.getSysRole();
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(new Date());
        return roleMapper.insert(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRoleUpdateParam roleUpdateParam) {
        SysRole role = roleUpdateParam.getSysRole();
        role.setUpdateBy(SecurityUtils.getUsername());
        role.setUpdateTime(new Date());
        // 修改角色信息
        roleMapper.updateById(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role.getRoleId(), roleUpdateParam.getDeptIds());
    }

    /**
     * 新增角色菜单信息
     *
     * @param roleId 角色对象
     */
    public int insertRoleMenu(Long roleId, Long[] menuIds) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param roleId  角色对象
     * @param deptIds 部门列表
     */
    public int insertRoleDept(Long roleId, Long[] deptIds) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<>();
        for (Long deptId : deptIds) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(roleId);
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (!list.isEmpty()) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(roleId);
            checkRoleDataScope(roleId);
            SysRoleDTO role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
