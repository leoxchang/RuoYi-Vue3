package com.truntao.system.domain.ro;

import lombok.Data;
import com.truntao.common.core.domain.entity.SysMenu;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 菜单权限对象 sys_menu
 *
 * @author truntao
 * @date 2023-08-28
 */
@Data
@ToString
public class SysMenuParam {

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @Size(max = 200, message = "路由地址不能超过200个字符")
    private String path;
    /**
     * 组件路径
     */
    @Size(max = 200, message = "组件路径不能超过255个字符")
    private String component;
    /**
     * 路由参数
     */
    private String query;
    /**
     * 是否为外链（0是 1否）
     */
    private Integer frame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private Integer cache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;
    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 备注
     */
    private String remark;

    public SysMenu getSysMenu() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName(getMenuName());
        sysMenu.setParentId(getParentId());
        sysMenu.setOrderNum(getOrderNum());
        sysMenu.setPath(getPath());
        sysMenu.setComponent(getComponent());
        sysMenu.setQuery(getQuery());
        sysMenu.setFrame(getFrame());
        sysMenu.setCache(getCache());
        sysMenu.setMenuType(getMenuType());
        sysMenu.setVisible(getVisible());
        sysMenu.setStatus(getStatus());
        sysMenu.setPerms(getPerms());
        sysMenu.setIcon(getIcon());
        sysMenu.setRemark(getRemark());
        return sysMenu;
    }
}
