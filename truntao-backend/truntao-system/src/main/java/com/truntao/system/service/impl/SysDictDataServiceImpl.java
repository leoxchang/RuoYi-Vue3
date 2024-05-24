package com.truntao.system.service.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.Page;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.ro.SysDictDataParam;
import com.truntao.system.domain.ro.SysDictDataUpdateParam;
import org.springframework.stereotype.Service;
import com.truntao.common.core.domain.entity.SysDictData;
import com.truntao.common.utils.DictUtils;
import com.truntao.system.mapper.SysDictDataMapper;
import com.truntao.system.service.ISysDictDataService;

import javax.annotation.Resource;

/**
 * 字典 业务层处理
 *
 * @author truntao
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {
    @Resource
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictDataParam 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public Page<SysDictDataDTO> selectDictDataList(SysDictDataParam dictDataParam) {
        Page<SysDictDataDTO> page = new Page<>();
        try (Page<SysDictData> list = dictDataMapper.selectDictDataList(dictDataParam.getSysDictData())) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysDictDataDTO::new).toList());
        }
        return page;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictDataDTO selectDictDataById(Long dictCode) {
        return new SysDictDataDTO(dictDataMapper.selectDictDataById(dictCode));
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictDataDTO data = selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas.stream().map(SysDictDataDTO::new).toList());
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictDataParam 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictDataParam dictDataParam) {
        SysDictData data = dictDataParam.getSysDictData();
        data.setCreateBy(SecurityUtils.getUsername());
        data.setCreateTime(new Date());
        int row = dictDataMapper.insert(data);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas.stream().map(SysDictDataDTO::new).toList());
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictDataUpdateParam 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictDataUpdateParam dictDataUpdateParam) {
        SysDictData data = dictDataUpdateParam.getSysDictData();
        data.setUpdateBy(SecurityUtils.getUsername());
        data.setUpdateTime(new Date());
        int row = dictDataMapper.updateById(data);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas.stream().map(SysDictDataDTO::new).toList());
        }
        return row;
    }
}
