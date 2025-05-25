import axios, {AxiosResponse} from 'axios'
import {ElLoading, ElMessage} from 'element-plus'
import {saveAs} from 'file-saver'
import {getToken} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {blobValidate} from '@/utils/truntao'

// 定义响应头接口
interface DownloadHeaders {
  'download-filename': string;

  [key: string]: string;
}

// 定义下载响应接口
interface DownloadResponse {
  data: Blob;
  headers: DownloadHeaders;
}

// 定义错误响应对象接口
interface ErrorResponse {
  code: string | number;
  msg?: string;
}

// 定义下载插件接口
interface DownloadPlugin {
  name(name: string, isDelete?: boolean): void;

  resource(resource: string): void;

  zip(url: string, name: string): void;

  saveAs(text: Blob | string, name: string, opts?: any): void;

  printErrMsg(data: Blob): Promise<void>;
}

const baseURL: string = import.meta.env.VITE_APP_BASE_API
let downloadLoadingInstance: any | null = null;

const downloadPlugin: DownloadPlugin = {
  name(name: string, isDelete: boolean = true): void {
    const url: string = baseURL + "/common/download?fileName=" + encodeURIComponent(name) + "&delete=" + isDelete
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: {'Authorization': 'Bearer ' + getToken()}
    }).then((res: AxiosResponse<Blob>) => {
      const isBlob: boolean = blobValidate(res.data);
      if (isBlob) {
        const blob: Blob = new Blob([res.data])
        this.saveAs(blob, decodeURIComponent(res.headers['download-filename'] as string))
      } else {
        this.printErrMsg(res.data);
      }
    })
  },
  resource(resource: string): void {
    const url: string = baseURL + "/common/download/resource?resource=" + encodeURIComponent(resource);
    axios({
      method: 'get',
      url: url,
      responseType: 'blob',
      headers: {'Authorization': 'Bearer ' + getToken()}
    }).then((res: AxiosResponse<Blob>) => {
      const isBlob: boolean = blobValidate(res.data);
      if (isBlob) {
        const blob: Blob = new Blob([res.data])
        this.saveAs(blob, decodeURIComponent(res.headers['download-filename'] as string))
      } else {
        this.printErrMsg(res.data);
      }
    })
  },
  zip(url: string, name: string): void {
    const downloadUrl: string = baseURL + url
    downloadLoadingInstance = ElLoading.service({
      text: "正在下载数据，请稍候",
      background: "rgba(0, 0, 0, 0.7)"
    })
    axios({
      method: 'get',
      url: downloadUrl,
      responseType: 'blob',
      headers: {'Authorization': 'Bearer ' + getToken()}
    }).then((res: AxiosResponse<Blob>) => {
      const isBlob: boolean = blobValidate(res.data);
      if (isBlob) {
        const blob: Blob = new Blob([res.data], {type: 'application/zip'})
        this.saveAs(blob, name)
      } else {
        this.printErrMsg(res.data);
      }
      if (downloadLoadingInstance) {
        downloadLoadingInstance.close();
        downloadLoadingInstance = null;
      }
    }).catch((error: any) => {
      console.error(error)
      ElMessage.error('下载文件出现错误，请联系管理员！')
      if (downloadLoadingInstance) {
        downloadLoadingInstance.close();
        downloadLoadingInstance = null;
      }
    })
  },
  saveAs(text: Blob | string, name: string, opts?: any): void {
    saveAs(text, name, opts);
  },
  async printErrMsg(data: Blob): Promise<void> {
    const resText: string = await data.text();
    const rspObj: ErrorResponse = JSON.parse(resText);
    const errMsg: string = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
    ElMessage.error(errMsg);
  }
}

export default downloadPlugin;
