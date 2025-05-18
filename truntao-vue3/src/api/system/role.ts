import request from '@/utils/request';
import type {
  Role,
  RoleQueryParams,
  AllocatedUserQueryParams,
  UnallocatedUserQueryParams,
  User
} from '@/types/system/role';
import type {Result, PageResult} from '@/types/global';

// 查询角色列表
export function listRole(query?: RoleQueryParams) {
  return request<any, Result<PageResult<Role>>>({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getRole(roleId: string | number) {
  return request<any, Result<Role>>({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data: Role) {
  return request<any, Result<any>>({
    url: '/system/role',
    method: 'post',
    data: data
  });
}

// 修改角色
export function updateRole(data: Role) {
  return request<any, Result<any>>({
    url: '/system/role',
    method: 'put',
    data: data
  });
}

// 角色数据权限
export function dataScope(data: Role) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  });
}

// 角色状态修改
export function changeRoleStatus(roleId: string | number, status: string) {
  const data = {
    roleId,
    status
  };
  return request({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  });
}

// 删除角色
export function delRole(roleId: string | number | (string | number)[]) {
  return request<any, Result<any>>({
    url: '/system/role/' + roleId,
    method: 'delete'
  });
}

// 查询角色已授权用户列表
export function allocatedUserList(query: AllocatedUserQueryParams) {
  return request<any, Result<PageResult<User>>>({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query: UnallocatedUserQueryParams) {
  return request<any, Result<PageResult<User>>>({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data: { userId: string | number; roleId: string | string[] | number | undefined }) {
  return request<any, Result<any>>({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data: { userIds: string, roleId: string| string[] | number | undefined }) {
  return request<any, Result<any>>({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    data: data
  })
}

// 授权用户选择
export function authUserSelectAll(data: { userIds: string | string[], roleId: string | number | undefined }) {
  return request<any, Result<any>>({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    data: data
  })
}

// 根据角色ID查询部门树结构
export function deptTreeSelect(roleId: string) {
  return request({
    url: '/system/role/deptTree/' + roleId,
    method: 'get'
  })
}

