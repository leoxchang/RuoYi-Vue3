package com.truntao.system.domain.ro;

import com.truntao.common.core.domain.entity.SysMenu;
import lombok.Data;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜单权限对象 sys_menu
 *
 * @author truntao
 * @date 2023-08-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SysMenuUpdateParam extends SysMenuParam {

    /**
     * 菜单ID
     */
    private Long menuId;
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
    /**
     * 备注
     */
    private String remark;

    @Override
    public SysMenu getSysMenu() {
        SysMenu sysMenu = super.getSysMenu();
        sysMenu.setMenuId(getMenuId());
        sysMenu.setCreateBy(getCreateBy());
        sysMenu.setCreateTime(getCreateTime());
        sysMenu.setUpdateBy(getUpdateBy());
        sysMenu.setUpdateTime(getUpdateTime());
        sysMenu.setRemark(getRemark());
        return sysMenu;
    }
}
