package com.truntao.web.controller.system;

import com.truntao.common.annotation.Log;
import com.truntao.common.constant.Constants;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.controller.BaseController;
import com.truntao.common.core.domain.R;
import com.truntao.common.core.domain.TreeSelect;
import com.truntao.common.core.domain.dto.SysMenuDTO;
import com.truntao.common.enums.BusinessType;
import com.truntao.system.domain.dto.RolesMenuDTO;
import com.truntao.system.domain.ro.SysMenuParam;
import com.truntao.system.domain.ro.SysMenuUpdateParam;
import com.truntao.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author truntao
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Resource
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermission('system:menu:list')")
    @GetMapping("/list")
    public R<List<SysMenuDTO>> list(SysMenuParam menuParam) {
        List<SysMenuDTO> menus = menuService.selectMenuList(menuParam, getUserId());
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public R<SysMenuDTO> getInfo(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree-select")
    public R<List<TreeSelect>> treeSelect(SysMenuParam menuParam) {
        List<SysMenuDTO> menus = menuService.selectMenuList(menuParam, getUserId());
        return R.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/role-menu-tree-select/{roleId}")
    public R<RolesMenuDTO> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<SysMenuDTO> menus = menuService.selectMenuList(getUserId());
        RolesMenuDTO rolesMenuDTO = new RolesMenuDTO();
        rolesMenuDTO.setCheckedKeys(menuService.selectMenuListByRoleId(roleId));
        rolesMenuDTO.setMenus(menuService.buildMenuTreeSelect(menus));
        return R.ok(rolesMenuDTO);
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermission('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysMenuParam menuParam) {
        if (!menuService.checkMenuNameUnique(null, menuParam.getMenuName(), menuParam.getParentId())) {
            return R.fail("新增菜单'" + menuParam.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menuParam.getFrame()) && !StringUtils.startsWithAny(menuParam.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return R.fail("新增菜单'" + menuParam.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        return R.ok(menuService.insertMenu(menuParam));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermission('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysMenuUpdateParam menuUpdateParam) {
        String errorMsg = "修改菜单'";
        if (!menuService.checkMenuNameUnique(menuUpdateParam.getMenuId(), menuUpdateParam.getMenuName(),
                menuUpdateParam.getParentId())) {
            return R.fail(errorMsg + menuUpdateParam.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menuUpdateParam.getFrame()) && !StringUtils.startsWithAny(menuUpdateParam.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return R.fail(errorMsg + menuUpdateParam.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menuUpdateParam.getMenuId().equals(menuUpdateParam.getParentId())) {
            return R.fail(errorMsg + menuUpdateParam.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return R.ok(menuService.updateMenu(menuUpdateParam));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermission('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public R<Integer> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return R.ok(menuService.deleteMenuById(menuId));
    }
}