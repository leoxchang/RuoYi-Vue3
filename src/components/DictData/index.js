import {useDict} from '@/utils/dict'

function install(app) {
  app.mixin({
    beforeCreate() {
      if (this.$options === undefined || this.$options.dicts === undefined || this.$options.dicts === null) {
        return
      }
      const data = reactive(this.$options.data());
      this.$options.data = () => data;
      for (let key of this.$options.dicts) {
        data[key] = useDict(key)[key];
      }
    },
  });
}

export default {
  install,
}
