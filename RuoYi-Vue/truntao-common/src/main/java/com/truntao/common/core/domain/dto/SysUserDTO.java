package com.truntao.common.core.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.common.annotation.Excels;
import com.truntao.common.core.domain.entity.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Objects;

import com.truntao.common.annotation.Excel;
import lombok.ToString;

/**
 * 用户信息对象 sys_user
 *
 * @author truntao
 * @date 2023-08-30
 */
@Data
@ToString
public class SysUserDTO {

    public SysUserDTO() {
    }

    public SysUserDTO(SysUser sysUser) {
        if (Objects.nonNull(sysUser)) {
            setUserId(sysUser.getUserId());
            setDeptId(sysUser.getDeptId());
            setUserName(sysUser.getUserName());
            setNickName(sysUser.getNickName());
            setEmail(sysUser.getEmail());
            setPhoneNumber(sysUser.getPhoneNumber());
            setSex(sysUser.getSex());
            setAvatar(sysUser.getAvatar());
            setPassword(sysUser.getPassword());
            setStatus(sysUser.getStatus());
            setDelFlag(sysUser.getDelFlag());
            setLoginIp(sysUser.getLoginIp());
            setLoginDate(sysUser.getLoginDate());
            setCreateBy(sysUser.getCreateBy());
            setCreateTime(sysUser.getCreateTime());
            setUpdateBy(sysUser.getUpdateBy());
            setUpdateTime(sysUser.getUpdateTime());
            setRemark(sysUser.getRemark());
        }
    }

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;
    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;
    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;
    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    @Excel(name = "用户类型", readConverterExp = "0=0系统用户")
    private String userType;
    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;
    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String avatar;
    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    private String delFlag;
    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;
    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDeptDTO dept;

    /**
     * 角色对象
     */
    private List<SysRoleDTO> roles;

    public boolean isAdmin() {
        return userId != null && 1L == userId;
    }
}
