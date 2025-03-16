import request from '@/utils/request'
import { Config, ConfigQueryParams, ConfigListResponse, ConfigDetailResponse } from '@/types/system/config'
import { CommonResponse } from '@/types/global'

// 查询参数列表
export function listConfig(query?: ConfigQueryParams): Promise<ConfigListResponse> {
  return request({
    url: '/system/config/list',
    method: 'get',
    params: query
  }) as unknown as Promise<ConfigListResponse>
}

// 查询参数详细
export function getConfig(configId: string | number): Promise<ConfigDetailResponse> {
  return request({
    url: '/system/config/' + configId,
    method: 'get'
  }) as unknown as Promise<ConfigDetailResponse>
}

// 根据参数键名查询参数值
export function getConfigKey(configKey: string): Promise<ConfigDetailResponse> {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  }) as unknown as Promise<ConfigDetailResponse>
}

// 新增参数配置
export function addConfig(data: Config): Promise<CommonResponse> {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 修改参数配置
export function updateConfig(data: Config): Promise<CommonResponse> {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 删除参数配置
export function delConfig(configId: string | number | (string | number)[]): Promise<CommonResponse> {
  return request({
    url: '/system/config/' + configId,
    method: 'delete'
  }) as unknown as Promise<CommonResponse>
}

// 刷新参数缓存
export function refreshCache(): Promise<CommonResponse> {
  return request({
    url: '/system/config/refreshCache',
    method: 'delete'
  }) as unknown as Promise<CommonResponse>
}
