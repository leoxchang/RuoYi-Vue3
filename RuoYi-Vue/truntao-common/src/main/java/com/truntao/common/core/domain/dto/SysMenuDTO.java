package com.truntao.common.core.domain.dto;

import com.truntao.common.core.domain.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import com.truntao.common.annotation.Excel;

/**
 * 菜单权限对象 sys_menu
 *
 * @author truntao
 * @date 2023-08-28
 */
@Data
@ToString
public class SysMenuDTO {

    public SysMenuDTO() {
    }

    public SysMenuDTO(SysMenu sysMenu) {
        if (Objects.nonNull(sysMenu)) {
            setMenuId(sysMenu.getMenuId());
            setMenuName(sysMenu.getMenuName());
            setParentId(sysMenu.getParentId());
            setOrderNum(sysMenu.getOrderNum());
            setPath(sysMenu.getPath());
            setComponent(sysMenu.getComponent());
            setQuery(sysMenu.getQuery());
            setFrame(sysMenu.getFrame());
            setCache(sysMenu.getCache());
            setMenuType(sysMenu.getMenuType());
            setVisible(sysMenu.getVisible());
            setStatus(sysMenu.getStatus());
            setPerms(sysMenu.getPerms());
            setIcon(sysMenu.getIcon());
            setCreateBy(sysMenu.getCreateBy());
            setCreateTime(sysMenu.getCreateTime());
            setUpdateBy(sysMenu.getUpdateBy());
            setUpdateTime(sysMenu.getUpdateTime());
            setRemark(sysMenu.getRemark());
        }
    }

    /**
     * 菜单ID
     */
    @Excel(name = "菜单ID")
    private Long menuId;
    /**
     * 菜单名称
     */
    @Excel(name = "菜单名称")
    private String menuName;
    /**
     * 父菜单ID
     */
    @Excel(name = "父菜单ID")
    private Long parentId;
    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @Excel(name = "路由地址")
    private String path;
    /**
     * 组件路径
     */
    @Excel(name = "组件路径")
    private String component;
    /**
     * 路由参数
     */
    @Excel(name = "路由参数")
    private String query;
    /**
     * 是否为外链（0是 1否）
     */
    @Excel(name = "是否为外链", readConverterExp = "0=是,1=否")
    private Integer frame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @Excel(name = "是否缓存", readConverterExp = "0=缓存,1=不缓存")
    private Integer cache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Excel(name = "菜单类型", readConverterExp = "M=目录,C=菜单,F=按钮")
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @Excel(name = "菜单状态", readConverterExp = "0=显示,1=隐藏")
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @Excel(name = "菜单状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /**
     * 权限标识
     */
    @Excel(name = "权限标识")
    private String perms;
    /**
     * 菜单图标
     */
    @Excel(name = "菜单图标")
    private String icon;
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
    /**
     * 子菜单
     */
    private List<SysMenuDTO> children = new ArrayList<>();
}
