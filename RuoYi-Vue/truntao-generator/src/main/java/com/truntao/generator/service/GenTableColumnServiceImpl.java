package com.truntao.generator.service;

import java.util.List;

import com.truntao.generator.domain.dto.GenTableColumnDTO;
import org.springframework.stereotype.Service;
import com.truntao.common.core.text.Convert;
import com.truntao.generator.domain.po.GenTableColumn;
import com.truntao.generator.mapper.GenTableColumnMapper;

import javax.annotation.Resource;

/**
 * 业务字段 服务层实现
 *
 * @author truntao
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {
    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumnDTO> selectGenTableColumnListByTableId(Long tableId) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(tableId).stream().map(GenTableColumnDTO::new).toList();
    }

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.insert(genTableColumn);
    }

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.updateById(genTableColumn);
    }

    /**
     * 删除业务字段对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGenTableColumnByIds(String ids) {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}
