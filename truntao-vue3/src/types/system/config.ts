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
