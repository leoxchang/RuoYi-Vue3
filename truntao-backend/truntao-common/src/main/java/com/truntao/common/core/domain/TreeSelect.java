package com.truntao.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.domain.dto.SysMenuDTO;
import com.truntao.common.core.domain.dto.SysDeptDTO;
import lombok.Data;

/**
 * TreeSelect树结构实体类
 *
 * @author truntao
 */
@Data
public class TreeSelect implements Serializable {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /** 节点禁用 */
    private boolean disabled = false;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(SysDeptDTO dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.disabled = Objects.equals(UserConstants.DEPT_DISABLE, dept.getStatus());
        this.children = dept.getChildren().stream().map(TreeSelect::new).toList();
    }

    public TreeSelect(SysMenuDTO menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).toList();
    }
}
