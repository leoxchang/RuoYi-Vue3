import { ComponentPublicInstance } from 'vue'
import { ComponentCustomProperties } from 'vue'

// 扩展 ComponentPublicInstance 类型
declare module 'vue' {
  interface ComponentCustomProperties {
    $modal: {
      msgSuccess: (message: string) => void;
      msgError: (message: string) => void;
      msgWarning: (message: string) => void;
      msgInfo: (message: string) => void;
      confirm: (message: string) => Promise<void>;
      alert: (message: string, title: string, options?: any) => void;
    };
    $refs: {
      [key: string]: any;
    };
    download: (url: string, params?: any, filename?: string) => void;
    resetForm: (formRef: string) => void;
    handleTree: (data: any[], id: string, parentId?: string, children?: string) => any[];
    useDict: (...args: string[]) => any;
    $tab: {
      closeOpenPage: (obj: { path: string }) => void;
    };
    parseTime: (time?: object | string | number | null, pattern?: string) => string | null;
  }
}

// 通用响应接口
export interface CommonResponse {
  code: number;
  msg: string;
  [key: string]: any;
}

// 分页响应接口
export interface PageResponse<T> {
  rows: T[];
  total: number;
}

// 通用列表响应接口
export interface ListResponse<T> extends CommonResponse {
  data: PageResponse<T>;
}

// 通用详情响应接口
export interface DetailResponse<T> extends CommonResponse {
  data: T;
}

// 声明 vite 环境变量
interface ImportMetaEnv {
  VITE_APP_BASE_API: string;
  [key: string]: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
} 