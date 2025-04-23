// 岗位管理相关的类型定义

// 岗位查询参数
export interface PostQueryParams {
  pageNum: number;
  pageSize: number;
  postCode?: string;
  postName?: string;
  status?: string;
}

// 岗位对象
export interface Post {
  postId?: string | number;
  postCode?: string;
  postName?: string;
  postSort?: number;
  status?: string;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  remark?: string;
}

// 岗位列表响应
export interface PostListResponse {
  code: number;
  msg: string;
  data: {
    rows: Post[];
    total: number;
  };
}

// 岗位详情响应
export interface PostDetailResponse {
  code: number;
  msg: string;
  data: Post;
}

// 通用响应
export interface Result {
  code: number;
  msg: string;
}
