package com.truntao.system.domain.ro;

import java.util.Date;
import java.util.Map;

import com.truntao.common.xss.Xss;
import lombok.Data;
import com.truntao.common.core.domain.entity.SysUser;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户信息对象 sys_user
 *
 * @author truntao
 * @date 2023-08-30
 */
@Data
@ToString
public class SysUserParam {
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 用户账号
     */
    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;
    /**
     * 用户昵称
     */
    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
    /**
     * 手机号码
     */
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 备注
     */
    private String remark;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

    private Map<String, Object> params;

    public SysUser getSysUser() {
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(getDeptId());
        sysUser.setUserName(getUserName());
        sysUser.setNickName(getNickName());
        sysUser.setEmail(getEmail());
        sysUser.setPhoneNumber(getPhoneNumber());
        sysUser.setSex(getSex());
        sysUser.setAvatar(getAvatar());
        sysUser.setPassword(getPassword());
        sysUser.setStatus(getStatus());
        sysUser.setLoginIp(getLoginIp());
        sysUser.setLoginDate(getLoginDate());
        sysUser.setRemark(getRemark());
        sysUser.setParams(getParams());
        return sysUser;
    }
}
