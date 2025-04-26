import request from '@/utils/request'
import {Config, ConfigQueryParams} from '@/types/system/config'
import {Result, PageResponse} from '@/types/global'

// 查询参数列表
export function listConfig(query?: ConfigQueryParams) {
  return request<any, Result<PageResponse<any>>>({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

// 查询参数详细
export function getConfig(configId: string | number) {
  return request<any, Result<Config>>({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

// 根据参数键名查询参数值
export function getConfigKey(configKey: string) {
  return request<any, Result<Config>>({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

// 新增参数配置
export function addConfig(data: Config) {
  return request<any, Result<any>>({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function updateConfig(data: Config) {
  return request<any, Result<any>>({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

// 删除参数配置
export function delConfig(configId: string | number | (string | number)[]) {
  return request<any, Result<any>>({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

// 刷新参数缓存
export function refreshCache() {
  return request<any, Result<any>>({
    url: '/system/config/refreshCache',
    method: 'delete'
  })
}
