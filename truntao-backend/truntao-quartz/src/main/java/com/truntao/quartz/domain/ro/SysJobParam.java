package com.truntao.quartz.domain.ro;

import com.truntao.quartz.domain.po.SysJob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 定时任务调度对象 sys_job
 *
 * @author truntao
 * @date 2023-09-13
 */
@Data
public class SysJobParam {
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 调用目标字符串
     */
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(min = 0, max = 500, message = "调用目标字符串长度不能超过500个字符")
    private String invokeTarget;
    /**
     * cron执行表达式
     */
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
    private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private String misfirePolicy;
    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;
    /**
     * 状态（0正常 1暂停）
     */
    private String status;
    /**
     * 备注信息
     */
    private String remark;

    public SysJob getSysJob() {
        SysJob sysJob = new SysJob();
        sysJob.setJobName(getJobName());
        sysJob.setJobGroup(getJobGroup());
        sysJob.setInvokeTarget(getInvokeTarget());
        sysJob.setCronExpression(getCronExpression());
        sysJob.setMisfirePolicy(getMisfirePolicy());
        sysJob.setConcurrent(getConcurrent());
        sysJob.setStatus(getStatus());
        sysJob.setRemark(getRemark());
        return sysJob;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("jobName", getJobName())
                .append("jobGroup", getJobGroup())
                .append("invokeTarget", getInvokeTarget())
                .append("cronExpression", getCronExpression())
                .append("misfirePolicy", getMisfirePolicy())
                .append("concurrent", getConcurrent())
                .append("status", getStatus())
                .append("remark", getRemark())
                .toString();
    }
}
