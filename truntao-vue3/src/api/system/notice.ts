import request from '@/utils/request'
import type {NoticeQueryParams, Notice} from '@/types/system/notice'
import type {PageResult, Result} from '@/types/global'
import {Config} from "@/types/system/config";

// 查询公告列表
export function listNotice(query: NoticeQueryParams) {
  return request<any, Result<PageResult<Notice>>>({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
export function getNotice(noticeId: string | number) {
  return request<any, Result<Config>>({
    url: '/system/notice/' + noticeId,
    method: 'get'
  })
}

// 新增公告
export function addNotice(data: Notice) {
  return request<any, Result<any>>({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateNotice(data: Notice) {
  return request<any, Result<any>>({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}

// 删除公告
export function delNotice(noticeId: string | number | (string | number)[]) {
  return request<any, Result<any>>({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  })
}
