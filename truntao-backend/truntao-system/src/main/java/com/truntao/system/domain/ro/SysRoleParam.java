package com.truntao.system.domain.ro;

import lombok.Data;
import com.truntao.common.core.domain.entity.SysRole;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * 角色信息对象 sys_role
 *
 * @author truntao
 * @date 2023-08-29
 */
@Data
@ToString
public class SysRoleParam {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(max = 100, message = "权限字符长度不能超过100个字符")
    private String roleKey;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;
    /**
     * 部门树选择项是否关联显示
     */
    private Boolean deptCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单组
     */
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;

    public SysRole getSysRole() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(getRoleName());
        sysRole.setRoleKey(getRoleKey());
        sysRole.setRoleSort(getRoleSort());
        sysRole.setDataScope(getDataScope());
        sysRole.setMenuCheckStrictly(Objects.equals(getMenuCheckStrictly(), Boolean.TRUE) ? 1 : 0);
        sysRole.setDeptCheckStrictly(Objects.equals(getDeptCheckStrictly(), Boolean.TRUE) ? 1 : 0);
        sysRole.setStatus(getStatus());
        sysRole.setRemark(getRemark());
        return sysRole;
    }
}
