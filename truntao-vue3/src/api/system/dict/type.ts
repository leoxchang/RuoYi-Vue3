import request from '@/utils/request';
import {
  DictType,
  DictTypeQueryParams,
  DictTypeResponse,
  DictDetailResponse,
  OptionSelectResponse
} from '@/types/system/dict';

// 查询字典类型列表
export function listType(query?: DictTypeQueryParams): Promise<DictTypeResponse> {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  }) as unknown as Promise<DictTypeResponse>
}

// 查询字典类型详细
export function getType(dictId: string | number): Promise<DictDetailResponse> {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'get'
  }) as unknown as Promise<DictDetailResponse>
}

// 新增字典类型
export function addType(data: DictType) {
  return request({
    url: '/system/dict/type',
    method: 'post',
    data: data
  }) as unknown as Promise<DictDetailResponse>
}

// 修改字典类型
export function updateType(data: DictType) {
  return request({
    url: '/system/dict/type',
    method: 'put',
    data: data
  });
}

// 删除字典类型
export function delType(dictId: string | number | (string | number)[]) {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'delete'
  });
}

// 刷新字典缓存
export function refreshCache() {
  return request({
    url: '/system/dict/type/refreshCache',
    method: 'delete'
  });
}

// 获取字典选择框列表
export function optionSelect(): Promise<OptionSelectResponse> {
  return request({
    url: '/system/dict/type/option-select',
    method: 'get'
  }) as unknown as Promise<OptionSelectResponse>;
}
