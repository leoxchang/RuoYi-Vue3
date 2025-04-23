import request from '@/utils/request';
import type {
  Role,
  RoleQueryParams,
  RoleListResponse,
  RoleDetailResponse,
  AllocatedUserQueryParams,
  UnallocatedUserQueryParams,
  UserListResponse
} from '@/types/system/role';
import type { Result } from '@/types/global';

// 查询角色列表
export function listRole(query?: RoleQueryParams): Promise<RoleListResponse> {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  }) as unknown as Promise<RoleListResponse>;
}

// 查询角色详细
export function getRole(roleId: string | number): Promise<RoleDetailResponse> {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  }) as unknown as Promise<RoleDetailResponse>;
}

// 新增角色
export function addRole(data: Role) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  });
}

// 修改角色
export function updateRole(data: Role) {
  return request({
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
  return request({
    url: '/system/role/' + roleId,
    method: 'delete'
  });
}

// 查询角色已授权用户列表
export function allocatedUserList(query: AllocatedUserQueryParams): Promise<UserListResponse> {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  }) as unknown as Promise<UserListResponse>
}

// 查询角色未授权用户列表
export function unallocatedUserList(query: UnallocatedUserQueryParams): Promise<UserListResponse> {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  }) as unknown as Promise<UserListResponse>
}

// 取消用户授权角色
export function authUserCancel(data: { userId: string | number; roleId: string | number }): Promise<Result<any>> {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 批量取消用户授权角色
export function authUserCancelAll(data: { userIds: (string | number)[]; roleId: string | number }): Promise<Result<any>> {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 授权用户选择
export function authUserSelectAll(data: { userIds: (string | number)[]; roleId: string | number }): Promise<Result<any>> {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 根据角色ID查询部门树结构
export function deptTreeSelect(roleId) {
  return request({
    url: '/system/role/deptTree/' + roleId,
    method: 'get'
  })
}

