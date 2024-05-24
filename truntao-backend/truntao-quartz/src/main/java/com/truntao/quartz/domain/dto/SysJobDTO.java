package com.truntao.quartz.domain.dto;

import com.truntao.quartz.domain.po.SysJob;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import com.truntao.common.annotation.Excel;

/**
 * 定时任务调度对象 sys_job
 *
 * @author truntao
 * @date 2023-09-13
 */
@Data
@ToString
public class SysJobDTO {

    public SysJobDTO() {
    }

    public SysJobDTO(SysJob sysJob) {
        if (Objects.nonNull(sysJob)) {
            setJobId(sysJob.getJobId());
            setJobName(sysJob.getJobName());
            setJobGroup(sysJob.getJobGroup());
            setInvokeTarget(sysJob.getInvokeTarget());
            setCronExpression(sysJob.getCronExpression());
            setMisfirePolicy(sysJob.getMisfirePolicy());
            setConcurrent(sysJob.getConcurrent());
            setStatus(sysJob.getStatus());
            setCreateBy(sysJob.getCreateBy());
            setCreateTime(sysJob.getCreateTime());
            setUpdateBy(sysJob.getUpdateBy());
            setUpdateTime(sysJob.getUpdateTime());
            setRemark(sysJob.getRemark());
        }
    }

    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private Long jobId;
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
     * cron执行表达式
     */
    @Excel(name = "cron执行表达式")
    private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @Excel(name = "计划执行错误策略", readConverterExp = "$column.readConverterExp()")
    private String misfirePolicy;
    /**
     * 是否并发执行（0允许 1禁止）
     */
    @Excel(name = "是否并发执行", readConverterExp = "$column.readConverterExp()")
    private String concurrent;
    /**
     * 状态（0正常 1暂停）
     */
    @Excel(name = "状态", readConverterExp = "$column.readConverterExp()")
    private String status;
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
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextValidTime;
}
