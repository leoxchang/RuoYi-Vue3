// 配置管理相关的类型定义

// 配置查询参数
export interface ConfigQueryParams {
  pageNum: number;
  pageSize: number;
  configName?: string;
  configKey?: string;
  configType?: string;
}

// 配置对象
export interface Config {
  configId?: string | number;
  configName?: string;
  configKey?: string;
  configValue?: string;
  configType?: string;
  remark?: string;
  createTime?: string;
}

// 配置列表响应
export interface ConfigListResponse {
  code: number;
  msg: string;
  data: {
    rows: Config[];
    total: number;
  };
}

// 配置详情响应
export interface ConfigDetailResponse {
  code: number;
  msg: string;
  data: Config;
}

// 通用响应
export interface CommonResponse {
  code: number;
  msg: string;
} 