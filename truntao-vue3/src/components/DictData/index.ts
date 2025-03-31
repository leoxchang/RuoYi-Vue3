import { App } from 'vue'
import { reactive } from 'vue'
import { useDict } from '@/utils/dict'

interface DictDataOptions {
  dicts?: string[]
  data?: () => Record<string, any>
}

function install(app: App) {
  app.mixin({
    beforeCreate() {
      const options = this.$options as DictDataOptions
      if (options.dicts === undefined || options.dicts === null) {
        return
      }
      const data = reactive(options.data?.() || {})
      options.data = () => data
      for (const key of options.dicts) {
        data[key] = useDict(key)[key]
      }
    },
  })
}

export default {
  install,
}
