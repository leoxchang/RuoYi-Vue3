import { DefineComponent } from 'vue'

declare const SvgIcon: DefineComponent<{
  iconClass: {
    type: StringConstructor
    required: true
  },
  className:{
    type: StringConstructor
    default: ''
  },
  color?: {
    type: StringConstructor
    default: ''
  }
}>

export default SvgIcon
