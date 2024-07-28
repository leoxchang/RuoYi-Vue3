package com.truntao.system.domain.ro;

import java.util.Date;

import com.truntao.common.core.domain.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Pattern;

/**
 * 用户信息对象 sys_user
 *
 * @author truntao
 * @date 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SysUserUpdateParam extends SysUserParam{

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Pattern(regexp = "^$|(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\\d!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*].{8,20}",message = "密码长度在12动20之间包含大/小写字母+数字+特殊字符")
    private String password;

    @Override
    public SysUser getSysUser() {
        SysUser sysUser = super.getSysUser();
        sysUser.setUserId(getUserId());
        sysUser.setDelFlag(getDelFlag());
        sysUser.setCreateBy(getCreateBy());
        sysUser.setCreateTime(getCreateTime());
        sysUser.setUpdateBy(getUpdateBy());
        sysUser.setUpdateTime(getUpdateTime());
        return sysUser;
    }
}
