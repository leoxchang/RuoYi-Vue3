package com.truntao.web.controller.system;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.domain.ro.SysDictDataParam;
import com.truntao.system.domain.ro.SysDictDataUpdateParam;
import com.truntao.system.service.ISysDictDataService;
import com.truntao.system.service.ISysDictTypeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据字典信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

    @Resource
    private ISysDictDataService dictDataService;
    @Resource
    private ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermission('system:dict:list')")
    @GetMapping("/list")
    public R<PageDTO<SysDictDataDTO>> list(SysDictDataParam dictDataParam) {
        startPage();
        Page<SysDictDataDTO> list = dictDataService.selectDictDataList(dictDataParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictDataParam dictDataParam) {
        List<SysDictDataDTO> list = dictDataService.selectDictDataList(dictDataParam);
        ExcelUtil<SysDictDataDTO> util = new ExcelUtil<>(SysDictDataDTO.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public R<SysDictDataDTO> getInfo(@PathVariable Long dictCode) {
        return R.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public R<List<SysDictDataDTO>> dictType(@PathVariable String dictType) {
        List<SysDictDataDTO> data = dictTypeService.selectDictDataByType(dictType);
        if (Objects.isNull(data)) {
            data = new ArrayList<>();
        }
        return R.ok(data);
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysDictDataParam dictDataParam) {
        return R.ok(dictDataService.insertDictData(dictDataParam));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysDictDataUpdateParam dictDataUpdateParam) {
        return R.ok(dictDataService.updateDictData(dictDataUpdateParam));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public R<Void> remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return R.ok();
    }
}
