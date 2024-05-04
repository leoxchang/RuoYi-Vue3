package com.truntao.system.domain.ro;

import com.truntao.common.core.domain.entity.SysDictData;
import lombok.Data;
import java.util.Date;

import lombok.EqualsAndHashCode;

/**
 * 字典数据对象 sys_dict_data
 *
 * @author truntao
 * @date 2023-08-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictDataUpdateParam extends SysDictDataParam {

    /**
     * 字典编码
     */
    private Long dictCode;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public SysDictData getSysDictData() {
        SysDictData sysDictData = super.getSysDictData();
        sysDictData.setDictCode(getDictCode());
        sysDictData.setCreateBy(getCreateBy());
        sysDictData.setCreateTime(getCreateTime());
        sysDictData.setUpdateBy(getUpdateBy());
        sysDictData.setUpdateTime(getUpdateTime());
        return sysDictData;
    }
}
