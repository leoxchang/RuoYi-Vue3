export interface FileItem {
  name: string;
  url: string;
  uid?: number;
}

export interface FileUploadProps {
  modelValue?: string | string[] | FileItem[];
  limit?: number;
  fileSize?: number;
  fileType?: string[];
  isShowTip?: boolean;
  disabled?: boolean;
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