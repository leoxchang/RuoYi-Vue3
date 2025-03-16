// 通知公告相关的类型定义

// 通知公告查询参数
export interface NoticeQueryParams {
  pageNum: number;
  pageSize: number;
  noticeTitle?: string;
  createBy?: string;
  noticeType?: string;
  status?: string;
}

// 通知公告对象
export interface Notice {
  noticeId?: string | number;
  noticeTitle?: string;
  noticeType?: string;
  noticeContent?: string;
  status?: string;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  remark?: string;
}

// 通知公告列表响应
export interface NoticeListResponse {
  code: number;
  msg: string;
  data: {
    rows: Notice[];
    total: number;
  };
}

// 通知公告详情响应
export interface NoticeDetailResponse {
  code: number;
  msg: string;
  data: Notice;
}

// 通用响应
export interface CommonResponse {
  code: number;
  msg: string;
} 