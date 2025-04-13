import { DefineComponent } from 'vue'
import type { RouteRecordRaw } from 'vue-router'

declare const TopNav: DefineComponent<{
  theme?: {
    type: StringConstructor
    default: string
  }
}>

export default TopNav