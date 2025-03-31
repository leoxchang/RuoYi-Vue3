import { DefineComponent } from 'vue'

declare const Breadcrumb: DefineComponent<{
  levelList: {
    type: ArrayConstructor
    required: true
    validator: (value: Array<{
      path: string
      meta: {
        title: string
        breadcrumb?: boolean
      }
      redirect?: string
    }>) => boolean
  }
}>

export default Breadcrumb