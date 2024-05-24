package com.truntao.generator.domain.po;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.truntao.common.core.domain.BaseEntity;

/**
 * 业务表 gen_table
 *
 * @author truntao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GenTable extends BaseEntity {

    /**
     * 编号
     */
    @TableId
    private Long tableId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 关联父表的表名
     */
    private String subTableName;

    /**
     * 本表关联父表的外键名
     */
    private String subTableFkName;

    /**
     * 实体类名称(首字母大写)
     */
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
     */
    private String tplCategory;

    /** 前端类型（element-ui模版 element-plus模版） */
    private String tplWebType;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成业务名
     */
    private String businessName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成作者
     */
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 其它生成选项
     */
    private String options;
    /**
     * 备注
     */
    private String remark;
}