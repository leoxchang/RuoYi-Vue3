package com.truntao.common.core.domain.dto;

import com.truntao.common.core.domain.entity.SysDictData;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import com.truntao.common.annotation.Excel;

/**
 * 字典数据对象 sys_dict_data
 *
 * @author truntao
 * @date 2023-08-25
 */
@Data
@ToString
public class SysDictDataDTO {

    public SysDictDataDTO() {
    }

    public SysDictDataDTO(SysDictData sysDictData) {
        if (Objects.nonNull(sysDictData)) {
            setDictCode(sysDictData.getDictCode());
            setDictSort(sysDictData.getDictSort());
            setDictLabel(sysDictData.getDictLabel());
            setDictValue(sysDictData.getDictValue());
            setDictType(sysDictData.getDictType());
            setCssClass(sysDictData.getCssClass());
            setListClass(sysDictData.getListClass());
            setIsDefault(sysDictData.getIsDefault());
            setStatus(sysDictData.getStatus());
            setCreateBy(sysDictData.getCreateBy());
            setCreateTime(sysDictData.getCreateTime());
            setUpdateBy(sysDictData.getUpdateBy());
            setUpdateTime(sysDictData.getUpdateTime());
            setRemark(sysDictData.getRemark());
        }
    }

    /**
     * 字典编码
     */
    @Excel(name = "字典编码")
    private Long dictCode;
    /**
     * 字典排序
     */
    @Excel(name = "字典排序")
    private Long dictSort;
    /**
     * 字典标签
     */
    @Excel(name = "字典标签")
    private String dictLabel;
    /**
     * 字典键值
     */
    @Excel(name = "字典键值")
    private String dictValue;
    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;
    /**
     * 样式属性（其他样式扩展）
     */
    @Excel(name = "样式属性", readConverterExp = "其=他样式扩展")
    private String cssClass;
    /**
     * 表格回显样式
     */
    @Excel(name = "表格回显样式")
    private String listClass;
    /**
     * 是否默认（Y是 N否）
     */
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    private String isDefault;
    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
