import request from '@/utils/request'

// 查询乡镇列表
export function listTown(query) {
  return request({
    url: '/system/town/list',
    method: 'get',
    params: query
  })
}

// 查询乡镇详细
export function getTown(id) {
  return request({
    url: '/system/town/' + id,
    method: 'get'
  })
}

// 新增乡镇
export function addTown(data) {
  return request({
    url: '/system/town',
    method: 'post',
    data: data
  })
}

// 修改乡镇
export function updateTown(data) {
  return request({
    url: '/system/town',
    method: 'put',
    data: data
  })
}

// 删除乡镇
export function delTown(id) {
  return request({
    url: '/system/town/' + id,
    method: 'delete'
  })
}
