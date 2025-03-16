import { DefineComponent } from 'vue'

declare const SvgIcon: DefineComponent<{
  iconClass: {
    type: StringConstructor
    required: true
  }
}>

export default SvgIcon 