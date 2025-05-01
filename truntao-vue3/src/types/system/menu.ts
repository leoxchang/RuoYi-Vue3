// 菜单管理相关的类型定义

// 菜单查询参数
export interface MenuQueryParams {
  menuName?: string;
  visible?: string;
  status?: string;
}

// 菜单对象
export interface Menu {
  menuId?: string | number;
  parentId?: string | number;
  menuName?: string;
  icon?: string;
  menuType?: string;
  orderNum?: number;
  isFrame?: string;
  isCache?: string;
  visible?: string;
  status?: string;
  perms?: string;
  component?: string;
  path?: string;
  query?: string;
  routeName?: string;
  children?: Menu[];
  createTime?: string;
}

// 菜单树选项
export interface MenuTreeOption {
  menuId: number;
  menuName: string;
  children?: MenuTreeOption[];
}
//
// // 菜单列表响应
// export interface MenuListResponse {
//   code: number;
//   msg: string;
//   data: Menu[];
// }
//
// // 菜单详情响应
// export interface MenuDetailResponse {
//   code: number;
//   msg: string;
//   data: Menu;
// }
//
// // 菜单树选择响应
// export interface MenuTreeSelectResponse {
//   code: number;
//   msg: string;
//   data: MenuTreeOption[];
// }
