package com.truntao.system.domain.ro;

import lombok.Data;
import com.truntao.common.core.domain.entity.SysDictData;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据对象 sys_dict_data
 *
 * @author truntao
 * @date 2023-08-25
 */
@Data
@ToString
public class SysDictDataParam {

    /**
     * 字典排序
     */
    private Long dictSort;
    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;
    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;
    /**
     * 样式属性（其他样式扩展）
     */
    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;
    /**
     * 表格回显样式
     */
    private String listClass;
    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public SysDictData getSysDictData() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictSort(getDictSort());
        sysDictData.setDictLabel(getDictLabel());
        sysDictData.setDictValue(getDictValue());
        sysDictData.setDictType(getDictType());
        sysDictData.setCssClass(getCssClass());
        sysDictData.setListClass(getListClass());
        sysDictData.setIsDefault(getIsDefault());
        sysDictData.setStatus(getStatus());
        sysDictData.setRemark(getRemark());
        return sysDictData;
    }
}
