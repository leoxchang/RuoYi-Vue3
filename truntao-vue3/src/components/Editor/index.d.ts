import { QuillEditor } from "@vueup/vue-quill";

export interface EditorProps {
  modelValue?: string;
  height?: number;
  minHeight?: number;
  readOnly?: boolean;
  fileSize?: number;
  type?: 'base64' | 'url';
}

export interface EditorOptions {
  theme: string;
  bounds: HTMLElement;
  debug: string;
  modules: {
    toolbar: Array<string | Array<string | object>>;
  };
  placeholder: string;
  readOnly: boolean;
}

export interface UploadResponse {
  code: number;
  fileName: string;
  msg?: string;
}

export interface EditorInstance {
  getQuill: () => QuillEditor;
} 