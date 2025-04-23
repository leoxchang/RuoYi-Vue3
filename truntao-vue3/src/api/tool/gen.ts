import request from '@/utils/request'
import {Result, ListResponse} from "@/types/global";
import {GenTableAll} from "@/types/tool/gen";

// 定义接口类型
interface Query {
  [key: string]: any
}

interface TableData {
  [key: string]: any
}

// 查询生成表数据
export function listTable(query: Query): Promise<ListResponse<any>> {
  return request({
    url: '/tool/gen/list',
    method: 'get',
    params: query
  }) as unknown as Promise<ListResponse<any>>
}

// 查询db数据库列表
export function listDbTable(query: Query): Promise<ListResponse<any>> {
  return request({
    url: '/tool/gen/db/list',
    method: 'get',
    params: query
  }) as unknown as Promise<ListResponse<any>>
}

// 查询表详细信息
export function getGenTable(tableId: string | string[] | number): Promise<Result<GenTableAll>> {
  return request({
    url: '/tool/gen/' + tableId,
    method: 'get'
  }) as unknown as Promise<Result<GenTableAll>>
}

// 修改代码生成信息
export function updateGenTable(data: TableData): Promise<Result<any>> {
  return request({
    url: '/tool/gen',
    method: 'put',
    data: data
  }) as unknown as Promise<Result<any>>
}

// 导入表
export function importTable(data: TableData): Promise<Result<any>> {
  return request({
    url: '/tool/gen/importTable',
    method: 'post',
    params: data
  }) as unknown as Promise<Result<any>>
}

// 创建表
export function createTable(data: TableData): Promise<Result<any>> {
  return request({
    url: '/tool/gen/createTable',
    method: 'post',
    params: data
  }) as unknown as Promise<Result<any>>
}

// 预览生成代码
export function previewTable(tableId: string | number) {
  return request({
    url: '/tool/gen/preview/' + tableId,
    method: 'get'
  })
}

// 删除表数据
export function delTable(tableId: string | number | (string | number)[]) {
  return request({
    url: '/tool/gen/' + tableId,
    method: 'delete'
  })
}

// 生成代码（自定义路径）
export function genCode(tableName: string) {
  return request({
    url: '/tool/gen/genCode/' + tableName,
    method: 'get'
  })
}

// 同步数据库
export function syncDb(tableName: string) {
  return request({
    url: '/tool/gen/syncDb/' + tableName,
    method: 'get'
  })
}
