package com.truntao.common.core.domain.dto;

import com.truntao.common.core.domain.entity.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import com.truntao.common.annotation.Excel;

/**
 * 角色信息对象 sys_role
 *
 * @author truntao
 * @date 2023-08-29
 */
@Data
@ToString
public class SysRoleDTO {

    public SysRoleDTO() {
    }

    public SysRoleDTO(SysRole sysRole) {
        if (Objects.nonNull(sysRole)) {
            setRoleId(sysRole.getRoleId());
            setRoleName(sysRole.getRoleName());
            setRoleKey(sysRole.getRoleKey());
            setRoleSort(sysRole.getRoleSort());
            setDataScope(sysRole.getDataScope());
            setMenuCheckStrictly(Objects.equals(sysRole.getMenuCheckStrictly(), 1));
            setDeptCheckStrictly(Objects.equals(sysRole.getDeptCheckStrictly(), 1));
            setStatus(sysRole.getStatus());
            setDelFlag(sysRole.getDelFlag());
            setCreateBy(sysRole.getCreateBy());
            setCreateTime(sysRole.getCreateTime());
            setUpdateBy(sysRole.getUpdateBy());
            setUpdateTime(sysRole.getUpdateTime());
            setRemark(sysRole.getRemark());
        }
    }

    /**
     * 角色ID
     */
    @Excel(name = "角色ID")
    private Long roleId;
    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @Excel(name = "角色权限字符串")
    private String roleKey;
    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Excel(name = "数据范围", readConverterExp = "1=：全部数据权限,2=：自定数据权限,3=：本部门数据权限,4=：本部门及以下数据权限")
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    @Excel(name = "菜单树选择项是否关联显示")
    private Boolean menuCheckStrictly;
    /**
     * 部门树选择项是否关联显示
     */
    @Excel(name = "部门树选择项是否关联显示")
    private Boolean deptCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    private String delFlag;
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
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    /**
     * 角色菜单权限
     */
    private Set<String> permissions;
}
