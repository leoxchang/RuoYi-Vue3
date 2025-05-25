package com.truntao.system.service.impl;

import com.github.pagehelper.Page;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.domain.dto.SysDictDataDTO;
import com.truntao.common.core.domain.dto.SysDictTypeDTO;
import com.truntao.common.core.domain.entity.SysDictData;
import com.truntao.common.core.domain.entity.SysDictType;
import com.truntao.common.exception.ServiceException;
import com.truntao.common.utils.DictUtils;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.ro.SysDictTypeParam;
import com.truntao.system.domain.ro.SysDictTypeUpdateParam;
import com.truntao.system.mapper.SysDictDataMapper;
import com.truntao.system.mapper.SysDictTypeMapper;
import com.truntao.system.service.ISysDictTypeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 * @author truntao
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    @Resource
    private SysDictTypeMapper dictTypeMapper;

    @Resource
    private SysDictDataMapper dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictTypeParam 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public Page<SysDictTypeDTO> selectDictTypeList(SysDictTypeParam dictTypeParam) {
        Page<SysDictTypeDTO> page = new Page<>();
        try (Page<SysDictType> list = dictTypeMapper.selectDictTypeList(dictTypeParam.getSysDictType())) {
            page.setTotal(list.getTotal());
            page.addAll(list.stream().map(SysDictTypeDTO::new).toList());
        }

        return page;
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictTypeDTO> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll().stream().map(SysDictTypeDTO::new).toList();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictDataDTO> selectDictDataByType(String dictType) {
        List<SysDictDataDTO> dictDatas = DictUtils.getDictCache(dictType);
        if (CollectionUtils.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType).stream().map(SysDictDataDTO::new).toList();
        if (CollectionUtils.isNotEmpty(dictDatas)) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return Collections.emptyList();
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictTypeDTO selectDictTypeById(Long dictId) {
        return new SysDictTypeDTO(dictTypeMapper.selectDictTypeById(dictId));
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictTypeDTO selectDictTypeByType(String dictType) {
        return new SysDictTypeDTO(dictTypeMapper.selectDictTypeByType(dictType));
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictTypeDTO dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
            dictTypeMapper.deleteDictTypeById(dictId);
            DictUtils.removeDictCache(dictType.getDictType());
        }
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache() {
        SysDictData dictData = new SysDictData();
        dictData.setStatus("0");
        try (Page<SysDictData> list = dictDataMapper.selectDictDataList(dictData)) {
            Map<String, List<SysDictDataDTO>> dictDataMap =
                    list.stream().map(SysDictDataDTO::new).collect(Collectors.groupingBy(SysDictDataDTO::getDictType));
            for (Map.Entry<String, List<SysDictDataDTO>> entry : dictDataMap.entrySet()) {
                DictUtils.setDictCache(entry.getKey(),
                        entry.getValue().stream().sorted(Comparator.comparing(SysDictDataDTO::getDictSort)).toList());
            }
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache() {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictTypeParam 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictTypeParam dictTypeParam) {
        SysDictType dict = dictTypeParam.getSysDictType();
        dict.setCreateBy(SecurityUtils.getUsername());
        dict.setCreateTime(new Date());
        int row = dictTypeMapper.insert(dict);
        if (row > 0) {
            DictUtils.setDictCache(dict.getDictType(), null);
        }
        return row;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictTypeUpdateParam 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictTypeUpdateParam dictTypeUpdateParam) {
        SysDictType dict = dictTypeUpdateParam.getSysDictType();
        dict.setUpdateBy(SecurityUtils.getUsername());
        dict.setUpdateTime(new Date());
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = dictTypeMapper.updateById(dict);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas.stream().map(SysDictDataDTO::new).toList());
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictId   字典Id
     * @param dictType 字典类型
     * @return 结果
     */
    @Override
    public boolean checkDictTypeUnique(Long dictId, String dictType) {
        dictId = Objects.isNull(dictId) ? -1L : dictId;
        SysDictType sysDictType = dictTypeMapper.checkDictTypeUnique(dictType);
        if (Objects.nonNull(sysDictType) && !Objects.equals(sysDictType.getDictId(), dictId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
