import request from '@/utils/request'
import type {PostQueryParams, Post} from '@/types/system/post'
import type {Result, PageResult} from '@/types/global'

// 查询岗位列表
export function listPost(query: PostQueryParams) {
  return request<any, Result<PageResult<Post>>>({
    url: '/system/post/list',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
export function getPost(postId: string | number) {
  return request<any, Result<Post>>({
    url: '/system/post/' + postId,
    method: 'get'
  })
}

// 新增岗位
export function addPost(data: Post) {
  return request<any, Result<any>>({
    url: '/system/post',
    method: 'post',
    data: data
  })
}

// 修改岗位
export function updatePost(data: Post) {
  return request<any, Result<any>>({
    url: '/system/post',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delPost(postId: string | number | (string | number)[]) {
  return request<any, Result<any>>({
    url: '/system/post/' + postId,
    method: 'delete'
  })
}
