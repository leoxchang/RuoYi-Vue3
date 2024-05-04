package com.truntao.system.domain.ro;

import lombok.Data;
import com.truntao.common.core.domain.entity.SysDept;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 部门对象 sys_dept
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysDeptParam {
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 联系电话
     */
    @Size(max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;
    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    public SysDept getSysDept() {
        SysDept sysDept = new SysDept();
        sysDept.setParentId(getParentId());
        sysDept.setAncestors(getAncestors());
        sysDept.setDeptName(getDeptName());
        sysDept.setOrderNum(getOrderNum());
        sysDept.setLeader(getLeader());
        sysDept.setPhone(getPhone());
        sysDept.setEmail(getEmail());
        sysDept.setStatus(getStatus());
        return sysDept;
    }
}
