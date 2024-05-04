package com.truntao.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.truntao.common.core.domain.BaseEntity;

/**
 * 字典类型表 sys_dict_type
 *
 * @author truntao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SysDictType extends BaseEntity {

    /**
     * 字典主键
     */
    @TableId
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    @TableField
    private String remark;
}
