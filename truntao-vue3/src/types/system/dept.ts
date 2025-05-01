// 部门管理相关的接口定义

// 部门查询参数
export interface DeptQueryParams {
  deptName?: string;
  status?: string;
}

// 部门信息
export interface Dept {
  deptId?: number | string;
  parentId?: number | string;
  deptName?: string;
  orderNum?: number;
  leader?: string;
  phone?: string;
  email?: string;
  status?: string;
  createTime?: string;
  children?: Dept[];
  hasChildren?: boolean;
  [key: string]: any;
}
