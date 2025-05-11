import request from '@/utils/request'
import {Result} from "@/types/global";

// 查询操作日志列表
export function list(query: any) {
  return request<any, Result<any>>({
    url: '/monitor/oper-log/list',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delOperLog(operId: string | number) {
  return request<any, Result<any>>({
    url: '/monitor/oper-log/' + operId,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperLog() {
  return request<any, Result<any>>({
    url: '/monitor/oper-log/clean',
    method: 'delete'
  })
}
