package com.truntao.generator.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.truntao.generator.domain.po.GenTableColumn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务字段 数据层
 *
 * @author truntao
 */
@Mapper
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {
    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 删除业务字段
     *
     * @param columnIds 列数据
     * @return 结果
     */
    int deleteGenTableColumns(List<Long> columnIds);

    /**
     * 批量删除业务字段
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGenTableColumnByIds(Long[] ids);
}
