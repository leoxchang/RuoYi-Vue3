package com.truntao.web.controller.system;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.domain.dto.SysConfigDTO;
import com.truntao.system.domain.ro.SysConfigParam;
import com.truntao.system.domain.ro.SysConfigUpdateParam;
import com.truntao.system.service.ISysConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 信息操作处理
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    @Resource
    private ISysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @PreAuthorize("@ss.hasPermission('system:config:list')")
    @GetMapping("/list")
    public R<PageDTO<SysConfigDTO>> list(SysConfigParam configParam) {
        startPage();
        Page<SysConfigDTO> list = configService.selectConfigList(configParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:config:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConfigParam configParam) {
        Page<SysConfigDTO> list = configService.selectConfigList(configParam);
        ExcelUtil<SysConfigDTO> util = new ExcelUtil<>(SysConfigDTO.class);
        util.exportExcel(response, list.getResult(), "参数数据");
    }

    /**
     * 根据参数编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:config:query')")
    @GetMapping(value = "/{configId}")
    public R<SysConfigDTO> getInfo(@PathVariable("configId") Long configId) {
        return R.ok(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public R<String> getConfigKey(@PathVariable("configKey") String configKey) {
        return R.ok(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:add')")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysConfigParam configParam) {
        if (!configService.checkConfigKeyUnique(null, configParam.getConfigKey())) {
            return R.fail("新增参数'" + configParam.getConfigName() + "'失败，参数键名已存在");
        }
        return R.ok(configService.insertConfig(configParam));
    }

    /**
     * 修改参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:edit')")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysConfigUpdateParam configUpdateParam) {
        if (!configService.checkConfigKeyUnique(configUpdateParam.getConfigId(), configUpdateParam.getConfigKey())) {
            return R.fail("修改参数'" + configUpdateParam.getConfigName() + "'失败，参数键名已存在");
        }
        return R.ok(configService.updateConfig(configUpdateParam));
    }

    /**
     * 删除参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public R<Void> remove(@PathVariable("configIds") Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return R.ok();
    }

    /**
     * 刷新参数缓存
     */
    @PreAuthorize("@ss.hasPermission('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public R<Void> refreshCache() {
        configService.resetConfigCache();
        return R.ok();
    }
}
