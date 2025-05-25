import { ElMessage, ElMessageBox, ElNotification, ElLoading } from 'element-plus'
import type { LoadingInstance } from 'element-plus/es/components/loading/src/loading'
import type { MessageBoxData } from 'element-plus/es/components/message-box/src/message-box.type'

// 定义消息框选项接口
interface MessageBoxOptions {
  type?: 'success' | 'warning' | 'info' | 'error'
  confirmButtonText?: string
  cancelButtonText?: string
}

// 定义加载选项接口
interface LoadingOptions {
  lock?: boolean
  text?: string
  background?: string
}

let loadingInstance: LoadingInstance | null = null;

export default {
  // 消息提示
  msg(content: string): void {
    ElMessage.info(content)
  },
  // 错误消息
  msgError(content: string): void {
    ElMessage.error(content)
  },
  // 成功消息
  msgSuccess(content: string): void {
    ElMessage.success(content)
  },
  // 警告消息
  msgWarning(content: string): void {
    ElMessage.warning(content)
  },
  // 弹出提示
  alert(content: string): Promise<MessageBoxData> {
    return ElMessageBox.alert(content, "系统提示")
  },
  // 错误提示
  alertError(content: string): Promise<MessageBoxData> {
    return ElMessageBox.alert(content, "系统提示", { type: 'error' })
  },
  // 成功提示
  alertSuccess(content: string): Promise<MessageBoxData> {
    return ElMessageBox.alert(content, "系统提示", { type: 'success' })
  },
  // 警告提示
  alertWarning(content: string): Promise<MessageBoxData> {
    return ElMessageBox.alert(content, "系统提示", { type: 'warning' })
  },
  // 通知提示
  notify(content: string): void {
    ElNotification.info(content)
  },
  // 错误通知
  notifyError(content: string): void {
    ElNotification.error(content);
  },
  // 成功通知
  notifySuccess(content: string): void {
    ElNotification.success(content)
  },
  // 警告通知
  notifyWarning(content: string): void {
    ElNotification.warning(content)
  },
  // 确认窗体
  confirm(content: string): Promise<MessageBoxData> {
    return ElMessageBox.confirm(content, "系统提示", {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: "warning",
    })
  },
  // 提交内容
  prompt(content: string): Promise<MessageBoxData> {
    return ElMessageBox.prompt(content, "系统提示", {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: "warning",
    })
  },
  // 打开遮罩层
  loading(content: string): void {
    loadingInstance = ElLoading.service({
      lock: true,
      text: content,
      background: "rgba(0, 0, 0, 0.7)",
    })
  },
  // 关闭遮罩层
  closeLoading(): void {
    if (loadingInstance) {
      loadingInstance.close();
      loadingInstance = null;
    }
  }
}