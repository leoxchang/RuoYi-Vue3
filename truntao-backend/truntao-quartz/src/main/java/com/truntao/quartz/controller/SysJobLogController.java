package com.truntao.quartz.controller;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.quartz.domain.dto.SysJobLogDTO;
import com.truntao.quartz.domain.ro.SysJobLogParam;
import com.truntao.quartz.service.ISysJobLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度日志操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {

    @Resource
    private ISysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:list')")
    @GetMapping("/list")
    public R<PageDTO<SysJobLogDTO>> list(SysJobLogParam sysJobLogParam) {
        startPage();
        Page<SysJobLogDTO> page = jobLogService.selectJobLogList(sysJobLogParam);
        return R.ok(new PageDTO<>(page));
    }

    /**
     * 导出定时任务调度日志列表
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:export')")
    @Log(title = "任务调度日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysJobLogParam sysJobLogParam) {
        List<SysJobLogDTO> list = jobLogService.selectJobLogList(sysJobLogParam);
        ExcelUtil<SysJobLogDTO> util = new ExcelUtil<>(SysJobLogDTO.class);
        util.exportExcel(response, list, "调度日志");
    }

    /**
     * 根据调度编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:query')")
    @GetMapping(value = "/{jobLogId}")
    public R<SysJobLogDTO> getInfo(@PathVariable Long jobLogId) {
        return R.ok(jobLogService.selectJobLogById(jobLogId));
    }


    /**
     * 删除定时任务调度日志
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:remove')")
    @Log(title = "定时任务调度日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobLogIds}")
    public R<Integer> remove(@PathVariable Long[] jobLogIds) {
        return R.ok(jobLogService.deleteJobLogByIds(jobLogIds));
    }

    /**
     * 清空定时任务调度日志
     */
    @PreAuthorize("@ss.hasPermission('monitor:job:remove')")
    @Log(title = "调度日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R<Void> clean() {
        jobLogService.cleanJobLog();
        return R.ok();
    }
}
