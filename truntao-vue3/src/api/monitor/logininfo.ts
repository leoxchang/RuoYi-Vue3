import request from '@/utils/request'
import {Result} from "@/types/global";

// 查询登录日志列表
export function list(query: any) {
  return request<any, Result<any>>({
    url: '/monitor/login-info/list',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function delLoginInfo(infoId: string | number) {
  return request<any, Result<any>>({
    url: '/monitor/login-info/' + infoId,
    method: 'delete'
  })
}

// 解锁用户登录状态
export function unlockLoginInfo(userName: string) {
  return request<any, Result<any>>({
    url: '/monitor/login-info/unlock/' + userName,
    method: 'get'
  })
}

// 清空登录日志
export function cleanLoginInfo() {
  return request<any, Result<any>>({
    url: '/monitor/login-info/clean',
    method: 'delete'
  })
}
