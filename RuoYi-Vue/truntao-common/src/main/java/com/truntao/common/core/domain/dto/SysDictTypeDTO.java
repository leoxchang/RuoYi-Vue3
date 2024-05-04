package com.truntao.common.core.domain.dto;

import com.truntao.common.core.domain.entity.SysDictType;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truntao.common.annotation.Excel;
import lombok.ToString;

/**
 * 字典类型对象 sys_dict_type
 *
 * @author truntao
 * @date 2023-08-25
 */
@Data
@ToString
public class SysDictTypeDTO {

    public SysDictTypeDTO() {
    }

    public SysDictTypeDTO(SysDictType sysDictType) {
        if (Objects.nonNull(sysDictType)) {
            setDictId(sysDictType.getDictId());
            setDictName(sysDictType.getDictName());
            setDictType(sysDictType.getDictType());
            setStatus(sysDictType.getStatus());
            setCreateBy(sysDictType.getCreateBy());
            setCreateTime(sysDictType.getCreateTime());
            setUpdateBy(sysDictType.getUpdateBy());
            setUpdateTime(sysDictType.getUpdateTime());
            setRemark(sysDictType.getRemark());
        }
    }

    /**
     * 字典主键
     */
    @Excel(name = "字典主键")
    private Long dictId;
    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    private String dictName;
    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    private String dictType;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
