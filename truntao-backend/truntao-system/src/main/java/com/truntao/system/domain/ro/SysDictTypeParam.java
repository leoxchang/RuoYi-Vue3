package com.truntao.system.domain.ro;

import com.truntao.common.core.domain.entity.SysDictType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 字典类型对象 sys_dict_type
 *
 * @author truntao
 * @date 2023-08-25
 */
@Data
public class SysDictTypeParam {

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    private String dictName;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    private String dictType;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    public SysDictType getSysDictType() {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setDictName(getDictName());
        sysDictType.setDictType(getDictType());
        sysDictType.setStatus(getStatus());
        sysDictType.setRemark(getRemark());
        return sysDictType;
    }
}
