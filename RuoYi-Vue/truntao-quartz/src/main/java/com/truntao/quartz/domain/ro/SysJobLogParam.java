package com.truntao.quartz.domain.ro;

import lombok.Data;
import com.truntao.quartz.domain.po.SysJobLog;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 定时任务调度日志对象 sys_job_log
 *
 * @author truntao
 * @date 2023-09-13
 */
@Data
public class SysJobLogParam {

    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 调用目标字符串
     */
    private String invokeTarget;
    /**
     * 日志信息
     */
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    private String status;
    /**
     * 异常信息
     */
    private String exceptionInfo;

    public SysJobLog getSysJobLog() {
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(getJobName());
        sysJobLog.setJobGroup(getJobGroup());
        sysJobLog.setInvokeTarget(getInvokeTarget());
        sysJobLog.setJobMessage(getJobMessage());
        sysJobLog.setStatus(getStatus());
        sysJobLog.setExceptionInfo(getExceptionInfo());
        return sysJobLog;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("jobName", getJobName())
                .append("jobGroup", getJobGroup())
                .append("invokeTarget", getInvokeTarget())
                .append("jobMessage", getJobMessage())
                .append("status", getStatus())
                .append("exceptionInfo", getExceptionInfo())
                .toString();
    }
}
