import request from '@/utils/request'
import {Result} from "@/types/global";

// 获取服务信息
export function getServer() {
  return request<any, Result<any>>({
    url: '/monitor/server',
    method: 'get'
  })
}
