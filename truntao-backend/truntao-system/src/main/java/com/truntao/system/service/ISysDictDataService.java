package com.truntao.system.service;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.system.domain.ro.SysDictDataParam;
import com.truntao.system.domain.ro.SysDictDataUpdateParam;

/**
 * 字典 业务层
 *
 * @author truntao
 */
public interface ISysDictDataService {
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictDataParam 字典数据信息
     * @return 字典数据集合信息
     */
    Page<SysDictDataDTO> selectDictDataList(SysDictDataParam dictDataParam);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictDataDTO selectDictDataById(Long dictCode);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    void deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增保存字典数据信息
     *
     * @param dictDataParam 字典数据信息
     * @return 结果
     */
    int insertDictData(SysDictDataParam dictDataParam);

    /**
     * 修改保存字典数据信息
     *
     * @param dictDataUpdateParam 字典数据信息
     * @return 结果
     */
    int updateDictData(SysDictDataUpdateParam dictDataUpdateParam);
}
