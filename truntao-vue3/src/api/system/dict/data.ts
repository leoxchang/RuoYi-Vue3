import request from '@/utils/request';
import type {DictData, DictDataQueryParams, DictType} from '@/types/system/dict';
import type {Result, PageResult} from "@/types/global";

// 查询字典数据列表
export function listData(query?: DictDataQueryParams) {
  return request<any, Result<PageResult<DictData>>>({
    url: '/system/dict/data/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getData(dictCode: string | number) {
  return request<any, Result<DictType>>({
    url: '/system/dict/data/' + dictCode,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType: string) {
  return request<any, Result<DictData[]>>({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}

// 新增字典数据
export function addData(data: DictData) {
  return request<any, Result<any>>({
    url: '/system/dict/data',
    method: 'post',
    data: data
  });
}

// 修改字典数据
export function updateData(data: DictData) {
  return request<any, Result<any>>({
    url: '/system/dict/data',
    method: 'put',
    data: data
  });
}

// 删除字典数据
export function delData(dictCode: string | number | (string | number)[]) {
  return request<any, Result<any>>({
    url: '/system/dict/data/' + dictCode,
    method: 'delete'
  });
}
