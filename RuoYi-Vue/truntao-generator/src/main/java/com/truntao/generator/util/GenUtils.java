package com.truntao.generator.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.truntao.common.constant.GenConstants;
import com.truntao.generator.config.GenConfig;
import com.truntao.generator.domain.po.GenTable;
import com.truntao.generator.domain.po.GenTableColumn;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 代码生成器 工具类
 *
 * @author truntao
 */
public class GenUtils {
    private GenUtils() {

    }

    /**
     * 初始化表信息
     */
    public static void initTable(GenTable genTable, String operatorName) {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.packageName);
        genTable.setModuleName(getModuleName(GenConfig.packageName));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(GenConfig.author);
        genTable.setCreateBy(operatorName);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column, Long tableId, String createBy) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(tableId);
        column.setCreateBy(createBy);
        // 设置java字段名
        column.setJavaField(CharSequenceUtil.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GenConstants.TYPE_STRING);
        column.setQueryType(GenConstants.QUERY_EQ);

        if (arraysContains(GenConstants.COLUMN_TYPE_STR, dataType) || arraysContains(GenConstants.COLUMN_TYPE_TEXT,
                dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMN_TYPE_TEXT, dataType) ?
                    GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GenConstants.COLUMN_TYPE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(GenConstants.HTML_DATETIME);
        } else if (arraysContains(GenConstants.COLUMN_TYPE_NUMBER, dataType)) {
            column.setHtmlType(GenConstants.HTML_INPUT);
            // 如果是浮点型 统一用BigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(GenConstants.TYPE_BIG_DECIMAL);
            } else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                // 如果是整形
                column.setJavaType(GenConstants.TYPE_INTEGER);
            } else if (str == null && StringUtils.contains(column.getColumnType(), "tinyint")) {
                // 长整形
                column.setJavaType(GenConstants.TYPE_INTEGER);
            } else {
                // 长整形
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setInsert(GenConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_EDIT, columnName) && !isPk(column.getPk())) {
            column.setEdit(GenConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_LIST, columnName) && !isPk(column.getPk())) {
            column.setList(GenConstants.REQUIRE);
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMN_NAME_NOT_QUERY, columnName) && !isPk(column.getPk())) {
            column.setQuery(GenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName) {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.tablePrefix;
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return CharSequenceUtil.upperFirst(CharSequenceUtil.toCamelCase(tableName));
    }

    /**
     * 批量替换前缀
     *
     * @param replaceValue 替换值
     * @param searchList   替换列表
     * @return java.lang.String
     */
    public static String replaceFirst(String replaceValue, String[] searchList) {
        String text = replaceValue;
        for (String searchString : searchList) {
            if (replaceValue.startsWith(searchString)) {
                text = replaceValue.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return RegExUtils.replaceAll(text, "(?:表|若依)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else if (StringUtils.indexOf(columnType, "unsigned") > 0) {
            return StringUtils.substringBefore(columnType, "unsigned").trim();
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }

    public static boolean isPk(String pk) {
        return pk != null && StringUtils.equals("1", pk);
    }

    public static boolean isEdit(String edit) {
        return edit != null && StringUtils.equals("1", edit);
    }

    public static boolean isList(String list) {
        return list != null && StringUtils.equals("1", list);
    }

    public static boolean isRequired(String required) {
        return required != null && StringUtils.equals("1", required);
    }

    public static boolean isInsert(String insert) {
        return insert != null && StringUtils.equals("1", insert);
    }

    public static boolean isQuery(String query) {
        return query != null && StringUtils.equals("1", query);
    }

    public static boolean isSuperColumn(String javaField) {
        return StringUtils.equalsAnyIgnoreCase(javaField,
                // BaseEntity
                "createBy", "createTime", "updateBy", "updateTime", "remark",
                // TreeEntity
                "parentName", "parentId", "orderNum", "ancestors");
    }

    public static boolean isUsableColumn(String javaField) {
        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
        return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
    }
}