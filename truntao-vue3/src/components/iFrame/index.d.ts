import { DefineComponent } from 'vue'

declare const IFrame: DefineComponent<{
  src: {
    type: StringConstructor
    required: true
  },
  height?: {
    type: StringConstructor
    default: string
  },
  loading?: {
    type: BooleanConstructor
    default: boolean
  }
}>

export default IFrame