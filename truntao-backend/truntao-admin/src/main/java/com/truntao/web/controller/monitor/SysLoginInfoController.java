package com.truntao.web.controller.monitor;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.framework.web.service.SysPasswordService;
import com.truntao.system.domain.dto.SysLoginInfoDTO;
import com.truntao.system.domain.ro.SysLoginInfoParam;
import com.truntao.system.service.ISysLoginInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author truntao
 */
@RestController
@RequestMapping("/monitor/login-info")
public class SysLoginInfoController extends BaseController {
    @Resource
    private ISysLoginInfoService loginInfoService;

    @Resource
    private SysPasswordService passwordService;

    @PreAuthorize("@ss.hasPermission('monitor:loginInfo:list')")
    @GetMapping("/list")
    public R<PageDTO<SysLoginInfoDTO>> list(SysLoginInfoParam loginInfoParam) {
        startPage();
        Page<SysLoginInfoDTO> list = loginInfoService.selectLoginInfoList(loginInfoParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('monitor:loginInfo:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLoginInfoParam loginInfoParam) {
        List<SysLoginInfoDTO> list = loginInfoService.selectLoginInfoList(loginInfoParam);
        ExcelUtil<SysLoginInfoDTO> util = new ExcelUtil<>(SysLoginInfoDTO.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermission('monitor:loginInfo:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public R<Integer> remove(@PathVariable("infoIds") Long[] infoIds) {
        return R.ok(loginInfoService.deleteLoginInfoByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermission('monitor:loginInfo:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R<Void> clean() {
        loginInfoService.cleanLoginInfo();
        return R.ok();
    }

    @PreAuthorize("@ss.hasPermission('monitor:loginInfo:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public R<Void> unlock(@PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return R.ok();
    }
}
