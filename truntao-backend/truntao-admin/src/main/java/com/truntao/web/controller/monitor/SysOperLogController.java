package com.truntao.web.controller.monitor;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.domain.dto.SysOperLogDTO;
import com.truntao.system.domain.ro.SysOperLogParam;
import com.truntao.system.service.ISysOperLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/oper-log")
public class SysOperLogController extends BaseController {
    @Resource
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermission('monitor:operlog:list')")
    @GetMapping("/list")
    public R<PageDTO<SysOperLogDTO>> list(SysOperLogParam operLogParam) {
        startPage();
        Page<SysOperLogDTO> list = operLogService.selectOperLogList(operLogParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLogParam operLogParam) {
        List<SysOperLogDTO> list = operLogService.selectOperLogList(operLogParam);
        ExcelUtil<SysOperLogDTO> util = new ExcelUtil<>(SysOperLogDTO.class);
        util.exportExcel(response, list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public R<Integer> remove(@PathVariable("operIds") Long[] operIds) {
        return R.ok(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermission('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public R<Void> clean() {
        operLogService.cleanOperLog();
        return R.ok();
    }
}
