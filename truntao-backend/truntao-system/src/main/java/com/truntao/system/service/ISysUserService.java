package com.truntao.system.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysUserDTO;
import com.truntao.common.core.domain.entity.SysUser;
import com.truntao.system.domain.ro.SysUserParam;
import com.truntao.system.domain.ro.SysUserUpdateParam;

/**
 * 用户 业务层
 *
 * @author truntao
 */
public interface ISysUserService {
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    Page<SysUserDTO> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserDTO> selectAllocatedList(SysUserParam user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserDTO> selectUnallocatedList(SysUserParam user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserDTO selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUserDTO selectUserById(Long userId);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserRoleGroup(String userName);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserPostGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param userId 用户Id
     * @param userName 用户名称
     * @return 结果
     */
    boolean checkUserNameUnique(Long userId, String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param userId 用户Id
     * @param phone 手机号
     * @return 结果
     */
    boolean checkPhoneUnique(Long userId, String phone);

    /**
     * 校验email是否唯一
     *
     * @param userId 用户Id
     * @param email email
     * @return 结果
     */
    boolean checkEmailUnique(Long userId, String email);

    /**
     * 校验用户是否允许操作
     *
     * @param userId 用户Id
     */
    void checkUserAllowed(Long userId);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    void checkUserDataScope(Long userId);

    /**
     * 新增用户信息
     *
     * @param userParam 用户信息
     * @return 结果
     */
    int insertUser(SysUserParam userParam);

    /**
     * 注册用户信息
     *
     * @param userParam 用户信息
     * @return 结果
     */
    boolean registerUser(SysUserParam userParam);

    /**
     * 修改用户信息
     *
     * @param userUpdateParam 用户信息
     * @return 结果
     */
    int updateUser(SysUserUpdateParam userUpdateParam);

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改用户基本信息
     *
     * @param userUpdateParam 用户信息
     * @return 结果
     */
    int updateUserProfile(SysUserUpdateParam userUpdateParam);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置用户密码
     *
     * @param userUpdateParam 用户信息
     * @return 结果
     */
    int resetPwd(SysUserUpdateParam userUpdateParam);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    int resetUserPwd(String userName, String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
