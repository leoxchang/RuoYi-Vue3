import request from '@/utils/request'
import type {
  Dept,
  DeptQueryParams,
  DeptListResponse,
  DeptDetailResponse
} from '@/types/system/dept';

// 查询部门列表
export function listDept(query?: DeptQueryParams) : Promise<DeptListResponse>{
  return request<DeptListResponse>({
    url: '/system/dept/list',
    method: 'get',
    params: query
  }) as unknown as Promise<DeptListResponse>;
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId: string | number): Promise<DeptListResponse> {
  return request({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  }) as unknown as Promise<DeptListResponse>;
}

// 查询部门详细
export function getDept(deptId: string | number): Promise<DeptDetailResponse> {
  return request({
    url: '/system/dept/' + deptId,
    method: 'get'
  }) as unknown as Promise<DeptDetailResponse>;
}

// 新增部门
export function addDept(data: Dept): Promise<any> {
  return request({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data: Dept): Promise<any> {
  return request({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(deptId: string | number): Promise<any> {
  return request({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}
