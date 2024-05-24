package com.truntao.system.domain.ro;

import com.truntao.common.core.domain.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 部门对象 sys_dept
 *
 * @author truntao
 * @date 2023-08-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptUpdateParam extends  SysDeptParam{

    /**
     * 部门id
     */
    private Long deptId;
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

    @Override
    public SysDept getSysDept() {
        SysDept sysDept = super.getSysDept();
        sysDept.setDeptId(getDeptId());
        sysDept.setCreateBy(getCreateBy());
        sysDept.setCreateTime(getCreateTime());
        sysDept.setUpdateBy(getUpdateBy());
        sysDept.setUpdateTime(getUpdateTime());
        sysDept.setDelFlag(sysDept.getDelFlag());
        return sysDept;
    }
}
