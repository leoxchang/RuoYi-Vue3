package com.truntao.system.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysRoleDTO;
import com.truntao.system.domain.po.SysUserRole;
import com.truntao.system.domain.ro.SysRoleParam;
import com.truntao.system.domain.ro.SysRoleUpdateParam;

/**
 * 角色业务层
 *
 * @author truntao
 */
public interface ISysRoleService {
    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    Page<SysRoleDTO> selectRoleList(SysRoleParam role);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleDTO> selectRolesByUserId(Long userId);

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<SysRoleDTO> selectRoleAll();

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    List<Long> selectRoleListByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRoleDTO selectRoleById(Long roleId);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleId 角色Id
     * @param roleName 角色名称
     * @return 结果
     */
    boolean checkRoleNameUnique(Long roleId,String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleId 角色Id
     * @param roleKey 角色key
     * @return 结果
     */
    boolean checkRoleKeyUnique(Long roleId,String roleKey);

    /**
     * 校验角色是否允许操作
     *
     * @param roleId 角色信息
     */
    void checkRoleAllowed(Long roleId);

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    void checkRoleDataScope(Long roleId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int insertRole(SysRoleParam role);

    /**
     * 修改保存角色信息
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    int updateRole(SysRoleUpdateParam roleUpdateParam);

    /**
     * 修改角色状态
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    int updateRoleStatus(SysRoleUpdateParam roleUpdateParam);

    /**
     * 修改数据权限信息
     *
     * @param roleUpdateParam 角色信息
     * @return 结果
     */
    int authDataScope(SysRoleUpdateParam roleUpdateParam);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    int deleteRoleByIds(Long[] roleIds);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteAuthUser(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUsers(Long roleId, Long[] userIds);

    boolean isAdmin(Long roleId);
}
