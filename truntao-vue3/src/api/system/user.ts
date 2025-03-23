import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/truntao";
import type {
  User,
  UserQueryParams,
  UserListResponse,
  UserDetailResponse,
  DeptTreeResponse
} from '@/types/system/user';

// 查询用户列表
export function listUser(query: UserQueryParams): Promise<UserListResponse> {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  }) as unknown as Promise<UserListResponse>
}

// 查询用户详细
export function getUser(userId?: string | number): Promise<UserDetailResponse> {
  return request({
    url: '/system/user/' + parseStrEmpty(userId),
    method: 'get'
  }) as unknown as Promise<UserDetailResponse>
}

// 新增用户
export function addUser(data: User): Promise<any> {
  return request({
    url: '/system/user',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data: User): Promise<any> {
  return request({
    url: '/system/user',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId: string | number | (string | number)[]): Promise<any> {
  return request({
    url: '/system/user/' + userId,
    method: 'delete'
  })
}

// 用户密码重置
export function resetUserPwd(userId: string | number, password: string): Promise<any> {
  const data = {
    userId,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId: string | number, status: string): Promise<any> {
  const data = {
    userId,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 定义用户个人信息响应类型
export interface UserProfileResponse {
  code: number;
  data: {
    user: User;
    roleGroup: string;
    postGroup: string;
  };
  msg: string;
}

// 查询用户个人信息
export function getUserProfile(): Promise<UserProfileResponse> {
  return request({
    url: '/system/user/profile',
    method: 'get'
  }) as unknown as Promise<UserProfileResponse>
}

// 修改用户个人信息
export function updateUserProfile(data: User): Promise<any> {
  return request({
    url: '/system/user/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword: string, newPassword: string): Promise<any> {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'put',
    data: data
  })
}

// 用户头像上传
export function uploadAvatar(data: FormData): Promise<any> {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data: data
  })
}

// 查询授权角色
export function getAuthRole(userId: string | number): Promise<any> {
  return request({
    url: '/system/user/authRole/' + userId,
    method: 'get'
  })
}

// 保存授权角色
export function updateAuthRole(data: any): Promise<any> {
  return request({
    url: '/system/user/authRole',
    method: 'put',
    params: data
  })
}

// 查询部门下拉树结构
export function deptTreeSelect(): Promise<DeptTreeResponse> {
  return request({
    url: '/system/user/deptTree',
    method: 'get'
  }) as unknown as Promise<DeptTreeResponse>
}
