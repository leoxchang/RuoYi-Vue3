// 字典类型查询参数
export interface DictTypeQueryParams {
  pageNum?: number;
  pageSize?: number;
  dictName?: string;
  dictType?: string;
  status?: string;
}

// 字典类型对象
export interface DictType {
  dictId?: number | string;
  dictName?: string;
  dictType?: string;
  status?: string;
  remark?: string;
  createTime?: string;
}

// 字典数据查询参数
export interface DictDataQueryParams {
  pageNum?: number;
  pageSize?: number;
  dictType?: string;
  dictLabel?: string;
  status?: string;
}

// 字典数据对象
export interface DictData {
  dictCode?: number | string;
  dictSort?: number;
  dictLabel?: string;
  dictValue?: string;
  dictType?: string;
  cssClass?: string;
  listClass?: string;
  status?: string;
  remark?: string;
  createTime?: string;
}

// 字典类型响应对象
export interface DictTypeResponse {
  code: number;
  msg: string;
  data: {
    rows: DictType[];
    total: number;
  };
}

// 字典数据响应对象
export interface DictDataResponse {
  code: number;
  msg: string;
  data: {
    rows: DictData[];
    total: number;
  };
}

// 字典详情响应对象
export interface DictDetailResponse {
  code: number;
  msg: string;
  data: DictType;
}

// 通用响应对象
export interface CommonResponse {
  code: number;
  msg: string;
  data: any;
}

// 字典选择框响应对象
export interface OptionSelectResponse {
  code: number;
  msg: string;
  data: DictType[];
}
