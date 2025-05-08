import request from '@/utils/request'
import {MenuQueryParams, Menu, MenuTreeOption, RoleMenu} from '@/types/system/menu'
import type {Result} from '@/types/global'

// 查询菜单列表
export function listMenu(query?: MenuQueryParams) {
  return request<any, Result<Menu[]>>({
    url: '/system/menu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(menuId: string | number) {
  return request<any, Result<Menu>>({
    url: '/system/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
export function treeSelect() {
  return request<any, Result<MenuTreeOption[]>>({
    url: '/system/menu/tree-select',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeSelect(roleId: string | number) {
  return request<any, Result<RoleMenu>>({
    url: '/system/menu/role-menu-tree-select/' + roleId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data: Menu) {
  return request<any, Result<any>>({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateMenu(data: Menu) {
  return request<any, Result<any>>({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(menuId: string | number) {
  return request<any, Result<any>>({
    url: '/system/menu/' + menuId,
    method: 'delete'
  })
}
