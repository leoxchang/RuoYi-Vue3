package com.truntao.generator.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.common.constant.GenConstants;
import com.truntao.generator.domain.po.GenTable;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 业务表 gen_table
 *
 * @author truntao
 */
@Data
public class GenTableDTO {

    public GenTableDTO() {
    }

    public GenTableDTO(GenTable genTable) {
        if (Objects.nonNull(genTable)) {
            setTableId(genTable.getTableId());
            setTableName(genTable.getTableName());
            setTableComment(genTable.getTableComment());
            setSubTableName(genTable.getSubTableName());
            setSubTableFkName(genTable.getSubTableFkName());
            setClassName(genTable.getClassName());
            setTplCategory(genTable.getTplCategory());
            setTplWebType(genTable.getTplWebType());
            setPackageName(genTable.getPackageName());
            setModuleName(genTable.getModuleName());
            setBusinessName(genTable.getBusinessName());
            setFunctionName(genTable.getFunctionName());
            setFunctionAuthor(genTable.getFunctionAuthor());
            setGenType(genTable.getGenType());
            setGenPath(genTable.getGenPath());
            setOptions(genTable.getOptions());
            setCreateBy(genTable.getCreateBy());
            setCreateTime(genTable.getCreateTime());
            setUpdateBy(genTable.getUpdateBy());
            setUpdateTime(genTable.getUpdateTime());
            setRemark(genTable.getRemark());
        }
    }

    /**
     * 编号
     */
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
     * 主键信息
     */
    private GenTableColumnDTO pkColumn;

    /**
     * 子表信息
     */
    private GenTableDTO subTable;

    /**
     * 表列信息
     */
    private List<GenTableColumnDTO> columns;

    /**
     * 其它生成选项
     */
    private String options;

    /**
     * 树编码字段
     */
    private String treeCode;

    /**
     * 树父编码字段
     */
    private String treeParentCode;

    /**
     * 树名称字段
     */
    private String treeName;

    /**
     * 上级菜单ID字段
     */
    private Long parentMenuId;

    /**
     * 上级菜单名称字段
     */
    private String parentMenuName;

    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String remark;

    public boolean isSub() {
        return isSub(this.tplCategory);
    }

    public static boolean isSub(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public boolean isTree() {
        return isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public boolean isCrud() {
        return isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public boolean isSuperColumn(String javaField) {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField) {
        if (isTree(tplCategory)) {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }
}