package com.truntao.quartz.controller;

import com.truntao.common.annotation.Log;
import com.truntao.common.constant.Constants;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.exception.job.TaskException;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.quartz.domain.dto.SysJobDTO;
import com.truntao.quartz.domain.po.SysJob;
import com.truntao.quartz.domain.ro.SysJobParam;
import com.truntao.quartz.domain.ro.SysJobUpdateParam;
import com.truntao.quartz.service.ISysJobService;
import com.truntao.quartz.util.CronUtils;
import com.truntao.quartz.util.ScheduleUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
    @Resource
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:list')")
    @GetMapping("/list")
    public R<PageDTO<SysJobDTO>> list(SysJobParam sysJobParam) {
        startPage();
        List<SysJobDTO> page = jobService.selectJobList(sysJobParam);
        return R.ok(new PageDTO<>(page));
    }

    /**
     * 导出定时任务列表
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:export')")
    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysJobParam sysJobParam) {
        List<SysJobDTO> list = jobService.selectJobList(sysJobParam);
        ExcelUtil<SysJobDTO> util = new ExcelUtil<>(SysJobDTO.class);
        util.exportExcel(response, list, "定时任务");
    }

    /**
     * 获取定时任务详细信息
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:query')")
    @GetMapping(value = "/{jobId}")
    public R<SysJobDTO> getInfo(@PathVariable("jobId") Long jobId) {
        return R.ok(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:add')")
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@RequestBody SysJobParam jobParam) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(jobParam.getCronExpression())) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(jobParam.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobParam.getInvokeTarget(), Constants.LOOKUP_LDAP,
                Constants.LOOKUP_LDAPS)) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobParam.getInvokeTarget(), Constants.HTTP,
                Constants.HTTPS)) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobParam.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(jobParam.getInvokeTarget())) {
            return R.fail("新增任务'" + jobParam.getJobName() + "'失败，目标字符串不在白名单内");
        }
        return R.ok(jobService.insertJob(jobParam));
    }

    /**
     * 修改定时任务
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:edit')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@RequestBody SysJobUpdateParam jobUpdateParam) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(jobUpdateParam.getCronExpression())) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(jobUpdateParam.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobUpdateParam.getInvokeTarget(), Constants.LOOKUP_LDAP,
                Constants.LOOKUP_LDAPS)) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobUpdateParam.getInvokeTarget(), Constants.HTTP,
                Constants.HTTPS)) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(jobUpdateParam.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(jobUpdateParam.getInvokeTarget())) {
            return R.fail("修改任务'" + jobUpdateParam.getJobName() + "'失败，目标字符串不在白名单内");
        }
        return R.ok(jobService.updateJob(jobUpdateParam));
    }

    /**
     * 定时任务状态修改
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@RequestBody SysJobUpdateParam jobUpdateParam) throws SchedulerException {
        return R.ok(jobService.changeStatus(jobUpdateParam));
    }

    /**
     * 定时任务立即执行一次
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run")
    public R<Void> run(@RequestBody SysJob job) throws SchedulerException {
        boolean result = jobService.run(job);
        return result ? R.ok() : R.fail("任务不存在或已过期！");
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public R<Void> remove(@PathVariable("jobIds") Long[] jobIds) throws SchedulerException {
        jobService.deleteJobByIds(jobIds);
        return R.ok();
    }
}
