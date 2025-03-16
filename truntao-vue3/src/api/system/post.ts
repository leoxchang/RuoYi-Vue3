import request from '@/utils/request'
import type { PostQueryParams, Post, PostListResponse, PostDetailResponse, CommonResponse } from '@/types/system/post'

// 查询岗位列表
export function listPost(query: PostQueryParams): Promise<PostListResponse> {
  return request({
    url: '/system/post/list',
    method: 'get',
    params: query
  }) as unknown as Promise<PostListResponse>
}

// 查询岗位详细
export function getPost(postId: string | number): Promise<PostDetailResponse> {
  return request({
    url: '/system/post/' + postId,
    method: 'get'
  }) as unknown as Promise<PostDetailResponse>
}

// 新增岗位
export function addPost(data: Post): Promise<CommonResponse> {
  return request({
    url: '/system/post',
    method: 'post',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 修改岗位
export function updatePost(data: Post): Promise<CommonResponse> {
  return request({
    url: '/system/post',
    method: 'put',
    data: data
  }) as unknown as Promise<CommonResponse>
}

// 删除岗位
export function delPost(postId: string | number | (string | number)[]): Promise<CommonResponse> {
  return request({
    url: '/system/post/' + postId,
    method: 'delete'
  }) as unknown as Promise<CommonResponse>
}
