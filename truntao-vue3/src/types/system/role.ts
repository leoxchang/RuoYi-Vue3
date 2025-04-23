// 角色查询参数
export interface RoleQueryParams {
  pageNum?: number;
  pageSize?: number;
  roleName?: string;
  roleKey?: string;
  status?: string;
  createTime?: string[];
}

// 角色对象
export interface Role {
  roleId?: number | string;
  roleName?: string;
  roleKey?: string;
  roleSort?: number;
  dataScope?: string;
  menuCheckStrictly?: boolean;
  deptCheckStrictly?: boolean;
  status?: string;
  delFlag?: string;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  remark?: string;
  menuIds?: (string | number)[];
  deptIds?: (string | number)[];
  admin?: boolean;
}

// 角色列表响应
export interface RoleListResponse {
  code: number;
  msg: string;
  data: {
    rows: Role[];
    total: number;
  };
}

// 角色详情响应
export interface RoleDetailResponse {
  code: number;
  msg: string;
  data: Role;
}

// 已分配用户查询参数
export interface AllocatedUserQueryParams {
  pageNum?: number;
  pageSize?: number;
  roleId?: string | number;
  userName?: string;
  phonenumber?: string;
}

// 未分配用户查询参数
export interface UnallocatedUserQueryParams extends AllocatedUserQueryParams {}

// 用户对象
export interface User {
  userId?: number | string;
  userName?: string;
  nickName?: string;
  email?: string;
  phonenumber?: string;
  sex?: string;
  status?: string;
  createTime?: string;
  remark?: string;
}

// 用户列表响应
export interface UserListResponse {
  code: number;
  msg: string;
  data: {
    rows: User[];
    total: number;
  };
}
