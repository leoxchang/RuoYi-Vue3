package com.truntao.system.domain.dto;

import com.truntao.common.core.domain.TreeSelect;
import lombok.Data;

import java.util.List;

/**
 * @author zhangxinlei
 * @date 2024-03-15
 */
@Data
public class RolesMenuDTO {

    private List<Long> checkedKeys;

    private List<TreeSelect> menus;
}
