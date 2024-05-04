package com.truntao.system.service.impl;

import java.util.*;

import com.truntao.common.core.domain.dto.SysMenuDTO;
import com.truntao.common.utils.SecurityUtils;
import com.truntao.system.domain.ro.SysMenuParam;
import com.truntao.system.domain.ro.SysMenuUpdateParam;
import com.truntao.system.domain.vo.MetaVo;
import com.truntao.system.domain.vo.RouterVo;
import com.truntao.system.mapper.SysMenuMapper;
import com.truntao.system.mapper.SysRoleMapper;
import com.truntao.system.mapper.SysRoleMenuMapper;
import com.truntao.system.service.ISysMenuService;
import com.truntao.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.truntao.common.constant.Constants;
import com.truntao.common.constant.UserConstants;
import com.truntao.common.core.domain.TreeSelect;
import com.truntao.common.core.domain.entity.SysMenu;
import com.truntao.common.core.domain.entity.SysRole;

import javax.annotation.Resource;

/**
 * 菜单 业务层处理
 *
 * @author truntao
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    private static final String SEPARATOR = "/";

    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private ISysUserService userService;

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDTO> selectMenuList(Long userId) {
        return selectMenuList(new SysMenuParam(), userId);
    }

    /**
     * 查询系统菜单列表
     *
     * @param menuParam 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDTO> selectMenuList(SysMenuParam menuParam, Long userId) {
        SysMenu menu = menuParam.getSysMenu();
        List<SysMenu> menuList;
        // 管理员显示所有菜单信息
        if (userService.isAdmin(userId)) {
            menuList = menuMapper.selectMenuList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList.stream().map(SysMenuDTO::new).toList();
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        List<String> perms = menuMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus.stream().map(SysMenuDTO::new).toList(), 0);
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, Objects.equals(Constants.YES, role.getMenuCheckStrictly()));
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenuDTO menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), Objects.equals(1, menu.getCache()),
                    menu.getPath()));
            List<SysMenuDTO> cMenus = menu.getChildren();
            if (CollectionUtils.isNotEmpty(cMenus) && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), Objects.equals(1,
                        menu.getCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menus) {
        List<SysMenuDTO> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(SysMenuDTO::getMenuId).toList();
        for (SysMenuDTO menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenuDTO> menus) {
        List<SysMenuDTO> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).toList();
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuDTO selectMenuById(Long menuId) {
        return new SysMenuDTO(menuMapper.selectMenuById(menuId));
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menuParam 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenuParam menuParam) {
        SysMenu menu = menuParam.getSysMenu();
        menu.setCreateBy(SecurityUtils.getUsername());
        menu.setCreateTime(new Date());
        return menuMapper.insert(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menuUpdateParam 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenuUpdateParam menuUpdateParam) {
        SysMenu menu = menuUpdateParam.getSysMenu();
        menu.setUpdateBy(SecurityUtils.getUsername());
        menu.setUpdateTime(new Date());
        return menuMapper.updateById(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuId   菜单Id
     * @param menuName 菜单名称
     * @param parentId 父级菜单
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(Long menuId, String menuName, Long parentId) {
        menuId = Objects.isNull(menuId) ? -1L : menuId;
        SysMenu info = menuMapper.checkMenuNameUnique(menuName, parentId);
        if (Objects.nonNull(info) && !Objects.equals(info.getMenuId(), menuId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenuDTO menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuDTO menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && Objects.equals(UserConstants.TYPE_DIR, menu.getMenuType())
                && Objects.equals(UserConstants.NO_FRAME, menu.getFrame())) {
            routerPath = SEPARATOR + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = SEPARATOR;
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuDTO menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenuDTO menu) {
        return menu.getParentId().intValue() == 0 && Objects.equals(UserConstants.TYPE_MENU, menu.getMenuType())
                && Objects.equals(menu.getFrame(), UserConstants.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenuDTO menu) {
        return Objects.equals(menu.getFrame(), UserConstants.NO_FRAME) && StringUtils.startsWithAny(menu.getPath(),
                Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuDTO menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuDTO> getChildPerms(List<SysMenuDTO> list, int parentId) {
        List<SysMenuDTO> returnList = new ArrayList<>();
        for (SysMenuDTO t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t    子节点
     */
    private void recursionFn(List<SysMenuDTO> list, SysMenuDTO t) {
        // 得到子节点列表
        List<SysMenuDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuDTO> getChildList(List<SysMenuDTO> list, SysMenuDTO t) {
        List<SysMenuDTO> tlist = new ArrayList<>();
        for (SysMenuDTO n : list) {
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDTO> list, SysMenuDTO t) {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, ".", ":"},
                new String[]{"", "", "", "/", "/"});
    }
}
