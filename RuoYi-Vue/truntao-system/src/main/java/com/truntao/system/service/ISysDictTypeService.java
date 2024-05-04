package com.truntao.system.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.common.core.domain.dto.SysDictTypeDTO;
import com.truntao.common.core.domain.entity.SysDictData;
import com.truntao.common.core.domain.entity.SysDictType;
import com.truntao.system.domain.ro.SysDictTypeParam;
import com.truntao.system.domain.ro.SysDictTypeUpdateParam;

/**
 * 字典 业务层
 *
 * @author truntao
 */
public interface ISysDictTypeService {
    /**
     * 根据条件分页查询字典类型
     *
     * @param dictTypeParam 字典类型信息
     * @return 字典类型集合信息
     */
    Page<SysDictTypeDTO> selectDictTypeList(SysDictTypeParam dictTypeParam);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<SysDictTypeDTO> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictDataDTO> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    SysDictTypeDTO selectDictTypeById(Long dictId);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    SysDictTypeDTO selectDictTypeByType(String dictType);

    /**
     * 批量删除字典信息
     *
     * @param dictIds 需要删除的字典ID
     */
    void deleteDictTypeByIds(Long[] dictIds);

    /**
     * 加载字典缓存数据
     */
    void loadingDictCache();

    /**
     * 清空字典缓存数据
     */
    void clearDictCache();

    /**
     * 重置字典缓存数据
     */
    void resetDictCache();

    /**
     * 新增保存字典类型信息
     *
     * @param dictTypeParam 字典类型信息
     * @return 结果
     */
    int insertDictType(SysDictTypeParam dictTypeParam);

    /**
     * 修改保存字典类型信息
     *
     * @param dictTypeUpdateParam 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictTypeUpdateParam dictTypeUpdateParam);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    boolean checkDictTypeUnique(Long dictTypeId, String dictTypeCode);
}
