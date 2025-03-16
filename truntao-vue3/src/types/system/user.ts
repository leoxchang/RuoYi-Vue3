// 用户管理相关的接口定义

// 用户查询参数
export interface UserQueryParams {
  pageNum: number;
  pageSize: number;
  userName?: string;
  phoneNumber?: string;
  status?: string;
  deptId?: number | string;
}

// 部门选项
export interface DeptOption {
  id: number | string;
  label: string;
  children?: DeptOption[];
  disabled?: boolean;
}

// 部门信息
export interface Dept {
  deptId: number | string;
  deptName: string;
  [key: string]: any;
}

// 用户信息
export interface User {
  userId?: number | string;
  userName?: string;
  nickName?: string;
  password?: string;
  phoneNumber?: string;
  email?: string;
  sex?: string;
  status?: string;
  remark?: string;
  deptId?: number | string;
  postIds?: (number | string)[];
  roleIds?: (number | string)[];
  dept?: Dept;
  createTime?: string;
  [key: string]: any;
}

// 岗位选项
export interface PostOption {
  postId: number | string;
  postName: string;
  status: string;
  [key: string]: any;
}

// 角色选项
export interface RoleOption {
  roleId: number | string;
  roleName: string;
  status: string;
  [key: string]: any;
}

// 用户列表响应
export interface UserListResponse {
  code: number;
  data: {
    rows: User[];
    total: number;
  };
  msg: string;
}

// 用户详情响应
export interface UserDetailResponse {
  code: number;
  data: {
    user: User;
    posts: PostOption[];
    roles: RoleOption[];
    postIds: (number | string)[];
    roleIds: (number | string)[];
  };
  msg: string;
}

// 部门树响应
export interface DeptTreeResponse {
  code: number;
  data: DeptOption[];
  msg: string;
}

// 上传配置
export interface UploadConfig {
  open: boolean;
  title: string;
  isUploading: boolean;
  updateSupport: number;
  headers: {
    Authorization: string;
  };
  url: string;
}

// 列配置
export interface ColumnConfig {
  key: number;
  label: string;
  visible: boolean;
} 