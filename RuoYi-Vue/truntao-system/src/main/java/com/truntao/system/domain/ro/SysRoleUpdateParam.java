package com.truntao.system.domain.ro;

import com.truntao.common.core.domain.entity.SysRole;
import lombok.Data;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 角色信息对象 sys_role
 *
 * @author truntao
 * @date 2023-08-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SysRoleUpdateParam extends SysRoleParam {

    /**
     * 角色ID
     */
    private Long roleId;
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
    /**
     * 备注
     */
    private String remark;

    @Override
    public SysRole getSysRole() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(getRoleId());
        sysRole.setRoleName(getRoleName());
        sysRole.setRoleKey(getRoleKey());
        sysRole.setRoleSort(getRoleSort());
        sysRole.setDataScope(getDataScope());
        sysRole.setStatus(getStatus());
        sysRole.setDelFlag(getDelFlag());
        sysRole.setCreateBy(getCreateBy());
        sysRole.setCreateTime(getCreateTime());
        sysRole.setUpdateBy(getUpdateBy());
        sysRole.setUpdateTime(getUpdateTime());
        sysRole.setRemark(getRemark());
        return sysRole;
    }
}
