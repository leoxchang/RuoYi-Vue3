import { DefineComponent } from 'vue'

declare const IconSelect: DefineComponent<{
  activeIcon?: {
    type: StringConstructor
    default: string
  }
}>

export default IconSelect 