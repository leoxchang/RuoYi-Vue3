import request from '@/utils/request';
import {
  DictType,
  DictTypeQueryParams,
} from '@/types/system/dict';
import type {Result, PageResult} from "@/types/global";

// 查询字典类型列表
export function listType(query?: DictTypeQueryParams) {
  return request<any, Result<PageResult<DictType>>>({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
export function getType(dictId: string | number) {
  return request<any, Result<DictType>>({
    url: '/system/dict/type/' + dictId,
    method: 'get'
  })
}

// 新增字典类型
export function addType(data: DictType) {
  return request<any,Result<any>>({
    url: '/system/dict/type',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateType(data: DictType) {
  return request<any,Result<any>>({
    url: '/system/dict/type',
    method: 'put',
    data: data
  });
}

// 删除字典类型
export function delType(dictId: string | number | (string | number)[]) {
  return request<any,Result<any>>({
    url: '/system/dict/type/' + dictId,
    method: 'delete'
  });
}

// 刷新字典缓存
export function refreshCache() {
  return request<any,Result<any>>({
    url: '/system/dict/type/refreshCache',
    method: 'delete'
  });
}

// 获取字典选择框列表
export function optionSelect(){
  return request<any,Result<DictType[]>>({
    url: '/system/dict/type/option-select',
    method: 'get'
  })
}
