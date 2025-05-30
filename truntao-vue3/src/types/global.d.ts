// 扩展 ComponentPublicInstance 类型
import {
  ElMessageBoxShortcutMethod,
  type MessageBoxData
} from "element-plus/es/components/message-box/src/message-box.type";
import type {DictData} from "@/utils/dict";

declare module 'vue' {
  interface ComponentCustomProperties {
    $modal: {
      msg(content: string): void;
      loading(message: string): unknown;
      msgSuccess: (message: string) => void;
      msgError(message: string): void
      msgWarning: (message: string) => void;
      confirm(content: string): Promise<MessageBoxData>;
      alert: (message: string, title: string, options?: any) => void;
      closeLoading: () => void;
      // 错误提示
      alertError(content: string): Promise<MessageBoxData>;
      // 成功提示
      alertSuccess(content: string): Promise<MessageBoxData>;
      // 警告提示
      alertWarning(content: string): Promise<MessageBoxData>;
      // 通知提示
      notify(content: string): void;
      // 错误通知
      notifyError(content: string): void;
      // 成功通知
      notifySuccess(content: string): void;
      // 警告通知
      notifyWarning(content: string): void;
      prompt(content: string): Promise<MessageBoxData>;
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
    useDict: (...args: string[]) =>  Ref<DictData[]>;
    $tab: {
      closeOpenPage: (obj: { path: string }) => void;
    };
    $alert: ElMessageBoxShortcutMethod;
    $prompt: ElMessageBoxShortcutMethod;
    parseTime: (time?: object | string | number | null, pattern?: string) => string | null;
    selectDictLabel: (datas: Map<string, DictData>, value: any) => string | undefined;
    addDateRange: (params: any, dateRange: string[]) => any;
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

export interface PageQuery {
  pageNum: number;
  pageSize: number;
}
