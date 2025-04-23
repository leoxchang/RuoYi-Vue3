import request from '@/utils/request'
import type { NoticeQueryParams, Notice, NoticeListResponse, NoticeDetailResponse } from '@/types/system/notice'
import type { Result } from '@/types/global'

// 查询公告列表
export function listNotice(query: NoticeQueryParams): Promise<NoticeListResponse> {
  return request({
    url: '/system/notice/list',
    method: 'get',
    params: query
  }) as unknown as Promise<NoticeListResponse>
}

// 查询公告详细
export function getNotice(noticeId: string | number): Promise<NoticeDetailResponse> {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'get'
  }) as unknown as Promise<NoticeDetailResponse>
}

// 新增公告
export function addNotice(data: Notice): Promise<Result<any>> {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 修改公告
export function updateNotice(data: Notice): Promise<Result<any>> {
  return request({
    url: '/system/notice',
    method: 'put',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 删除公告
export function delNotice(noticeId: string | number | (string | number)[]): Promise<Result<any>> {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  }) as unknown as Promise<Result<any>>
}
