package com.truntao.web.controller.system;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.system.domain.dto.SysNoticeDTO;
import com.truntao.system.domain.ro.SysNoticeParam;
import com.truntao.system.domain.ro.SysNoticeUpdateParam;
import com.truntao.system.service.ISysNoticeService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告 信息操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Resource
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermission('system:notice:list')")
    @GetMapping("/list")
    public R<PageDTO<SysNoticeDTO>> list(SysNoticeParam noticeParam) {
        startPage();
        Page<SysNoticeDTO> list = noticeService.selectNoticeList(noticeParam);
        return R.ok(new PageDTO<>(list));
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public R<SysNoticeDTO> getInfo(@PathVariable("noticeId") Long noticeId) {
        return R.ok(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermission('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysNoticeParam noticeParam) {
        return R.ok(noticeService.insertNotice(noticeParam));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermission('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysNoticeUpdateParam noticeUpdateParam) {
        return R.ok(noticeService.updateNotice(noticeUpdateParam));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermission('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public R<Integer> remove(@PathVariable("noticeIds") Long[] noticeIds) {
        return R.ok(noticeService.deleteNoticeByIds(noticeIds));
    }
}
