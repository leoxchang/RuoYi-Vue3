package com.truntao.quartz.domain.ro;

import com.truntao.quartz.domain.po.SysJob;
import lombok.Data;

import java.util.Date;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 定时任务调度对象 sys_job
 *
 * @author truntao
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysJobUpdateParam extends SysJobParam{

    /**
     * 任务ID
     */
    private Long jobId;
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
     * 备注信息
     */
    private String remark;

    @Override
    public SysJob getSysJob() {
        SysJob sysJob = super.getSysJob();
        sysJob.setJobId(getJobId());
        sysJob.setCreateBy(getCreateBy());
        sysJob.setCreateTime(getCreateTime());
        sysJob.setUpdateBy(getUpdateBy());
        sysJob.setUpdateTime(getUpdateTime());
        sysJob.setRemark(getRemark());
        return sysJob;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("jobId", getJobId())
                .append("jobName", getJobName())
                .append("jobGroup", getJobGroup())
                .append("invokeTarget", getInvokeTarget())
                .append("cronExpression", getCronExpression())
                .append("misfirePolicy", getMisfirePolicy())
                .append("concurrent", getConcurrent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
