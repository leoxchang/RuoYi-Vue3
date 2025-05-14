export interface FileItem {
  name: string;
  url: string;
  uid?: number;
}

export interface FileUploadProps {
  modelValue?: string | string[] | FileItem[];
  action?: string;
  data: object;
  limit?: number;
  fileSize?: number;
  fileType?: string[];
  isShowTip?: boolean;
  disabled?: boolean;
  drag: boolean;
}

export interface UploadResponse {
  code: number;
  msg: string;
  data: {
    fileName: string;
  };
}

export interface FileUploadInstance {
  handleRemove: (file: File) => void;
}
