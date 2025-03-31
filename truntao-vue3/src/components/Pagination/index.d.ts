import { DefineComponent } from 'vue'

declare const Pagination: DefineComponent<{
  total: {
    type: NumberConstructor
    required: true
  }
}>

export default Pagination
