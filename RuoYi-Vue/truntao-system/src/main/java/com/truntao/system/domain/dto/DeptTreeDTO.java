package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.TreeSelect;
import lombok.Data;

import java.util.List;

/**
 * dept tree dto
 *
 * @author zhangxinlei
 * @date 2023-09-06
 */
@Data
public class DeptTreeDTO {

    private List<Long> checkedKeys;

    private List<TreeSelect> depts;
}
