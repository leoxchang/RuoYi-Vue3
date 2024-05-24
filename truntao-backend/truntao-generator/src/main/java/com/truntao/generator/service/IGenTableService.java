package com.truntao.generator.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.truntao.generator.domain.po.GenTable;
import com.truntao.generator.domain.dto.GenTableDTO;
import com.truntao.generator.domain.ro.GenTableParam;

/**
 * 业务 服务层
 *
 * @author truntao
 */
 public interface IGenTableService {
    /**
     * 查询业务列表
     *
     * @param genTableParam 业务信息
     * @return 业务集合
     */
     Page<GenTableDTO> selectGenTableList(GenTableParam genTableParam);

    /**
     * 查询据库列表
     *
     * @param genTableParam 业务信息
     * @return 数据库表集合
     */
     Page<GenTableDTO> selectDbTableList(GenTableParam genTableParam);

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
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
     GenTableDTO selectGenTableById(Long id);

    /**
     * 修改业务
     *
     * @param genTableParam 业务信息
     */
     void updateGenTable(GenTableParam genTableParam);

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据ID
     */
     void deleteGenTableByIds(Long[] tableIds);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     */
     void importGenTable(List<GenTable> tableList,String operName);

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
     Map<String, String> previewCode(Long tableId);

    /**
     * 生成代码（下载方式）
     *
     * @param tableName 表名称
     * @return 数据
     */
     byte[] downloadCode(String tableName);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     */
     void generatorCode(String tableName);

    /**
     * 同步数据库
     *
     * @param tableName 表名称
     */
     void syncDb(String tableName);

    /**
     * 批量生成代码（下载方式）
     *
     * @param tableNames 表数组
     * @return 数据
     */
     byte[] downloadCode(String[] tableNames);

    /**
     * 修改保存参数校验
     *
     * @param genTableParam 业务信息
     */
     void validateEdit(GenTableParam genTableParam);

    /**
     * 创建表
     *
     * @param sql 创建表语句
     * @return 结果
     */
    boolean createTable(String sql);
}
