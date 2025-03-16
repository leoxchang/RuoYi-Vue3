import request from '@/utils/request'
import type { MenuQueryParams, Menu, MenuListResponse, MenuDetailResponse, MenuTreeSelectResponse, CommonResponse } from '@/types/system/menu'

// 查询菜单列表
export function listMenu(query?: MenuQueryParams): Promise<MenuListResponse> {
  return request({
    url: '/system/menu/list',
    method: 'get',
    params: query
  }) as unknown as Promise<MenuListResponse>
}

// 查询菜单详细
export function getMenu(menuId: string | number): Promise<MenuDetailResponse> {
  return request({
    url: '/system/menu/' + menuId,
    method: 'get'
  }) as unknown as Promise<MenuDetailResponse>
}

// 查询菜单下拉树结构
export function treeselect(): Promise<MenuTreeSelectResponse> {
  return request({
    url: '/system/menu/tree-select',
    method: 'get'
  }) as unknown as Promise<MenuTreeSelectResponse>
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId: string | number): Promise<MenuTreeSelectResponse> {
  return request({
    url: '/system/menu/role-menu-tree-select/' + roleId,
    method: 'get'
  }) as unknown as Promise<MenuTreeSelectResponse>
}

// 新增菜单
export function addMenu(data: Menu): Promise<CommonResponse> {
  return request({
    url: '/system/menu',
    method: 'post',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 修改菜单
export function updateMenu(data: Menu): Promise<CommonResponse> {
  return request({
    url: '/system/menu',
    method: 'put',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 删除菜单
export function delMenu(menuId: string | number): Promise<CommonResponse> {
  return request({
    url: '/system/menu/' + menuId,
    method: 'delete'
  }) as unknown as Promise<CommonResponse>
}
