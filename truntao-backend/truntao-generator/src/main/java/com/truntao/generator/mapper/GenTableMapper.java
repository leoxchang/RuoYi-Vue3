package com.truntao.generator.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.truntao.generator.domain.po.GenTable;
import com.truntao.generator.domain.dto.GenTableDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务 数据层
 *
 * @author truntao
 */
@Mapper
 public interface GenTableMapper extends BaseMapper<GenTable> {
    /**
     * 查询业务列表
     *
     * @param genTable 业务信息
     * @return 业务集合
     */
     Page<GenTableDTO> selectGenTableList(GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
     Page<GenTableDTO> selectDbTableList(GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
     List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
     List<GenTableDTO> selectGenTableAll();

    /**
     * 查询表ID业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
     GenTableDTO selectGenTableById(Long id);

    /**
     * 查询表名称业务信息
     *
     * @param tableName 表名称
     * @return 业务信息
     */
     GenTableDTO selectGenTableByName(String tableName);

    /**
     * 批量删除业务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteGenTableByIds(Long[] ids);


    /**
     * 创建表
     *
     * @param sql 表结构
     * @return 结果
     */
    int createTable(String sql);
}
