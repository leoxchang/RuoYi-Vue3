import { DefineComponent } from 'vue'

declare const DictTag: DefineComponent<{
  options: {
    type: ArrayConstructor
    default: () => Array<{
      value: string | number
      label: string
      elTagType?: string
      elTagClass?: string
    }>
  }
  value: {
    type: [NumberConstructor, StringConstructor, ArrayConstructor]
  }
  showValue: {
    type: BooleanConstructor
    default: boolean
  }
  separator: {
    type: StringConstructor
    default: string
  }
}>

export default DictTag