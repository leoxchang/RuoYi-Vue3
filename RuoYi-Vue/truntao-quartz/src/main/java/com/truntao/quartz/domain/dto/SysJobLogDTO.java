package com.truntao.quartz.domain.dto;

import com.truntao.quartz.domain.po.SysJobLog;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.common.annotation.Excel;

/**
 * 定时任务调度日志对象 sys_job_log
 *
 * @author truntao
 * @date 2023-09-13
 */
@Data
public class SysJobLogDTO {

    public SysJobLogDTO() {
    }

    public SysJobLogDTO(SysJobLog sysJobLog) {
        if (Objects.nonNull(sysJobLog)) {
            setJobLogId(sysJobLog.getJobLogId());
            setJobName(sysJobLog.getJobName());
            setJobGroup(sysJobLog.getJobGroup());
            setInvokeTarget(sysJobLog.getInvokeTarget());
            setJobMessage(sysJobLog.getJobMessage());
            setStatus(sysJobLog.getStatus());
            setExceptionInfo(sysJobLog.getExceptionInfo());
            setCreateTime(sysJobLog.getCreateTime());
        }
    }

    /**
     * 任务日志ID
     */
    @Excel(name = "任务日志ID")
    private Long jobLogId;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String jobName;
    /**
     * 任务组名
     */
    @Excel(name = "任务组名")
    private String jobGroup;
    /**
     * 调用目标字符串
     */
    @Excel(name = "调用目标字符串")
    private String invokeTarget;
    /**
     * 日志信息
     */
    @Excel(name = "日志信息")
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    @Excel(name = "执行状态", readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    private String exceptionInfo;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
