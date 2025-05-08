// 扩展 ComponentPublicInstance 类型
import {ElMessageBoxShortcutMethod} from "element-plus/es/components/message-box/src/message-box.type";

declare module 'vue' {
  interface ComponentCustomProperties {
    $modal: {
      loading(message: string): unknown;
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
    $download: {
      zip: (url: string, filename: string) => void;
    };
    download: (url: string, params?: any, filename?: string) => void;
    resetForm: (formRef: string) => void;
    handleTree: (data: any[], id: string, parentId?: string, children?: string) => any[];
    useDict: (...args: string[]) => any;
    $tab: {
      closeOpenPage: (obj: { path: string }) => void;
    };
    $alert:ElMessageBoxShortcutMethod;
    $prompt: ElMessageBoxShortcutMethod;
    parseTime: (time?: object | string | number | null, pattern?: string) => string | null;
  }
}

// 通用响应接口
export interface Result<T> {
  code: number;
  msg: string;
  data: T;
}

// 分页响应接口
export interface PageResult<T> {
  rows: T[];
  total: number;
}

// 声明 vite 环境变量
interface ImportMetaEnv {
  VITE_APP_BASE_API: string;

  [key: string]: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
