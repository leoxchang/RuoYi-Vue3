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

// 已分配用户查询参数
export interface AllocatedUserQueryParams {
  pageNum?: number;
  pageSize?: number;
  roleId?: string | number;
  userName?: string;
  phoneNumber?: string;
}

// 未分配用户查询参数
export interface UnallocatedUserQueryParams extends AllocatedUserQueryParams {}

// 用户对象
export interface User {
  userId?: number | string;
  userName?: string;
  nickName?: string;
  email?: string;
  phoneNumber?: string;
  sex?: string;
  status?: string;
  createTime?: string;
  remark?: string;
}
