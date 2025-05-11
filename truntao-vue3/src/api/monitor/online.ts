import request from '@/utils/request'
import {Result} from "@/types/global";

// 查询在线用户列表
export function list(query: any) {
  return request<any, Result<any>>({
    url: '/monitor/online/list',
    method: 'get',
    params: query
  })
}

// 强退用户
export function forceLogout(tokenId: string) {
  return request<any, Result<any>>({
    url: '/monitor/online/' + tokenId,
    method: 'delete'
  })
}
