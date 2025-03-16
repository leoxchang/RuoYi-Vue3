import request from '@/utils/request';
import type { DictData, DictDataQueryParams, DictDataResponse, DictDetailResponse } from '@/types/system/dict';

// 查询字典数据列表
export function listData(query?: DictDataQueryParams): Promise<DictDataResponse> {
  return request({
    url: '/system/dict/data/list',
    method: 'get',
    params: query
  }) as unknown as Promise<DictDataResponse>;
}

// 查询字典数据详细
export function getData(dictCode: string | number): Promise<DictDetailResponse> {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'get'
  }) as unknown as Promise<DictDetailResponse>;
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType: string): Promise<DictData[]> {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  }) as unknown as Promise<DictData[]>;
}

// 新增字典数据
export function addData(data: DictData) {
  return request({
    url: '/system/dict/data',
    method: 'post',
    data: data
  });
}

// 修改字典数据
export function updateData(data: DictData) {
  return request({
    url: '/system/dict/data',
    method: 'put',
    data: data
  });
}

// 删除字典数据
export function delData(dictCode: string | number | (string | number)[]) {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'delete'
  });
}
