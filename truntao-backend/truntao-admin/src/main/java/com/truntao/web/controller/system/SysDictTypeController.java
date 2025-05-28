package com.truntao.web.controller.system;

import com.github.pagehelper.Page;
import com.truntao.common.annotation.Log;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.dto.SysDictTypeDTO;
import com.truntao.common.core.page.PageDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.common.utils.poi.ExcelUtil;
import com.truntao.system.domain.ro.SysDictTypeParam;
import com.truntao.system.domain.ro.SysDictTypeUpdateParam;
import com.truntao.system.service.ISysDictTypeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Resource
    private ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermission('system:dict:list')")
    @GetMapping("/list")
    public R<PageDTO<SysDictTypeDTO>> list(SysDictTypeParam dictTypeParam) {
        startPage();
        Page<SysDictTypeDTO> list = dictTypeService.selectDictTypeList(dictTypeParam);
        return R.ok(new PageDTO<>(list));
    }

    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictTypeParam dictTypeParam) {
        List<SysDictTypeDTO> list = dictTypeService.selectDictTypeList(dictTypeParam);
        ExcelUtil<SysDictTypeDTO> util = new ExcelUtil<>(SysDictTypeDTO.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    @GetMapping(value = "/{dictId}")
    public R<SysDictTypeDTO> getInfo(@PathVariable("dictId") Long dictId) {
        return R.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysDictTypeParam dictTypeParam) {
        if (!dictTypeService.checkDictTypeUnique(null, dictTypeParam.getDictType())) {
            return R.fail("新增字典'" + dictTypeParam.getDictName() + "'失败，字典类型已存在");
        }
        return R.ok(dictTypeService.insertDictType(dictTypeParam));
    }

    /**
     * 修改字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysDictTypeUpdateParam dictTypeUpdateParam) {
        if (!dictTypeService.checkDictTypeUnique(dictTypeUpdateParam.getDictId(), dictTypeUpdateParam.getDictType())) {
            return R.fail("修改字典'" + dictTypeUpdateParam.getDictName() + "'失败，字典类型已存在");
        }
        return R.ok(dictTypeService.updateDictType(dictTypeUpdateParam));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermission('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public R<Void> remove(@PathVariable("dictIds") Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return R.ok();
    }

    /**
     * 刷新字典缓存
     */
    @PreAuthorize("@ss.hasPermission('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public R<Void> refreshCache() {
        dictTypeService.resetDictCache();
        return R.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/option-select")
    public R<List<SysDictTypeDTO>> optionSelect() {
        List<SysDictTypeDTO> dictTypes = dictTypeService.selectDictTypeAll();
        return R.ok(dictTypes);
    }
}
