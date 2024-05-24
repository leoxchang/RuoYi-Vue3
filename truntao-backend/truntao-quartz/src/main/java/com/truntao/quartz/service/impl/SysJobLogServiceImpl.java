package com.truntao.quartz.service.impl;

import com.github.pagehelper.Page;
import com.truntao.quartz.domain.dto.SysJobLogDTO;
import com.truntao.quartz.domain.ro.SysJobLogParam;
import org.springframework.stereotype.Service;
import com.truntao.quartz.domain.po.SysJobLog;
import com.truntao.quartz.mapper.SysJobLogMapper;
import com.truntao.quartz.service.ISysJobLogService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author truntao
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {
    @Resource
    private SysJobLogMapper jobLogMapper;

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLogParam 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public Page<SysJobLogDTO> selectJobLogList(SysJobLogParam jobLogParam) {
        Page<SysJobLogDTO> page = new Page<>();
        try (Page<SysJobLog> list = jobLogMapper.selectJobLogList(jobLogParam.getSysJobLog())) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysJobLogDTO::new).toList());
        }
        return page;
    }

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLogDTO selectJobLogById(Long jobLogId) {
        return new SysJobLogDTO(jobLogMapper.selectJobLogById(jobLogId));
    }

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog) {
        jobLog.setCreateTime(new Date());
        jobLogMapper.insert(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds) {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }
}
