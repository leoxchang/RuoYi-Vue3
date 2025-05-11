import request from '@/utils/request'
import {Result} from "@/types/global";

// 查询调度日志列表
export function listJobLog(query: any) {
  return request<any, Result<any>>({
    url: '/monitor/jobLog/list',
    method: 'get',
    params: query
  })
}

// 删除调度日志
export function delJobLog(jobLogId: string | number) {
  return request<any, Result<any>>({
    url: '/monitor/jobLog/' + jobLogId,
    method: 'delete'
  })
}

// 清空调度日志
export function cleanJobLog() {
  return request<any, Result<any>>({
    url: '/monitor/jobLog/clean',
    method: 'delete'
  })
}
