package com.truntao.common.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.common.annotation.Excel;
import com.truntao.common.core.domain.entity.SysDept;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 部门对象 sys_dept
 *
 * @author truntao
 * @date 2023-08-24
 */
@Data
@ToString
public class SysDeptDTO {

    public SysDeptDTO() {
    }

    public SysDeptDTO(SysDept sysDept) {
        if (Objects.nonNull(sysDept)) {
            setDeptId(sysDept.getDeptId());
            setParentId(sysDept.getParentId());
            setAncestors(sysDept.getAncestors());
            setDeptName(sysDept.getDeptName());
            setOrderNum(sysDept.getOrderNum());
            setLeader(sysDept.getLeader());
            setPhone(sysDept.getPhone());
            setEmail(sysDept.getEmail());
            setStatus(sysDept.getStatus());
            setDelFlag(sysDept.getDelFlag());
            setCreateBy(sysDept.getCreateBy());
            setCreateTime(sysDept.getCreateTime());
            setUpdateBy(sysDept.getUpdateBy());
            setUpdateTime(sysDept.getUpdateTime());
        }
    }

    /**
     * 部门id
     */
    @Excel(name = "部门id")
    private Long deptId;
    /**
     * 父部门id
     */
    @Excel(name = "父部门id")
    private Long parentId;
    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    private String ancestors;
    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;
    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer orderNum;
    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String leader;
    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;
    /**
     * 部门状态（0正常 1停用）
     */
    @Excel(name = "部门状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,1=代表删除")
    private String delFlag;
    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 子部门
     */
    private List<SysDeptDTO> children = new ArrayList<>();
}
