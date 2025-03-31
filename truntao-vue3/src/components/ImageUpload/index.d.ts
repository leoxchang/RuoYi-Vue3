import { DefineComponent } from 'vue'

declare const ImageUpload: DefineComponent<{
  modelValue: {
    type: [String, Object, Array]
  },
  limit: {
    type: NumberConstructor
    default: number
  },
  fileSize: {
    type: NumberConstructor
    default: number
  },
  fileType: {
    type: ArrayConstructor
    default: () => string[]
  },
  isShowTip: {
    type: BooleanConstructor
    default: boolean
  }
}, {
  handleBeforeUpload: (file: File) => boolean
  handleExceed: () => void
  handleUploadSuccess: (res: { code: number; data: { fileName: string }; msg?: string }, file: File) => void
  handleDelete: (file: File) => boolean
  handleUploadError: () => void
  handlePictureCardPreview: (file: { url: string }) => void
}> & {
  $refs: {
    imageUpload: any
  }
}

export default ImageUpload