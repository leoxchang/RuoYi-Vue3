import request from '@/utils/request'
import type {Dept, DeptQueryParams} from '@/types/system/dept';
import type { Result} from '@/types/global'

// 查询部门列表
export function listDept(query?: DeptQueryParams) {
  return request<any, Result<Dept[]>>({
    url: '/system/dept/list',
    method: 'get',
    params: query
  });
}

// 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId: string | number) {
  return request<any, Result<Dept[]>>({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  });
}

// 查询部门详细
export function getDept(deptId: string | number) {
  return request<any, Result<Dept>>({
    url: '/system/dept/' + deptId,
    method: 'get'
  });
}

// 新增部门
export function addDept(data: Dept) {
  return request<any, Result<any>>({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data: Dept) {
  return request<any, Result<any>>({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(deptId: string | number) {
  return request<any, Result<any>>({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}
