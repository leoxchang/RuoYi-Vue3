<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(String(item.value))">
        <span
          v-if="(item.elTagType === 'default' || item.elTagType === '') && (item.elTagClass === '' || item.elTagClass == null)"
          :key="item.value"
          :index="index"
          :class="item.elTagClass"
        >{{ item.label + " " }}</span>
        <el-tag
          v-else
          :disable-transitions="true"
          :key="item.value + ''"
          :index="index"
          :type="item.elTagType"
          :class="item.elTagClass"
        >{{ item.label + " " }}</el-tag>
      </template>
    </template>
    <template v-if="unMatch && showValue">
      {{ handleArray(unMatchArray) }}
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import type { PropType } from 'vue'

interface DictOption {
  value: string | number
  label: string
  elTagType?: string
  elTagClass?: string
}

// 记录未匹配的项
const unMatchArray = ref<string[]>([])

const props = defineProps({
  // 数据
  options: {
    type: Array as PropType<DictOption[]>,
    default: () => [],
  },
  // 当前的值
  value: {
    type: [Number, String, Array] as PropType<number | string | (string | number)[]>,
    default: '',
  },
  // 当未找到匹配的数据时，显示value
  showValue: {
    type: Boolean,
    default: true,
  },
  separator: {
    type: String,
    default: ",",
  }
})

const values = computed(() => {
  if (props.value === null || typeof props.value === 'undefined' || props.value === '') return [];
  return Array.isArray(props.value) ? props.value.map(item => '' + item) : String(props.value).split(props.separator);
});

const unMatch = computed(() => {
  unMatchArray.value = [];
  // 没有value不显示
  if (props.value === null || typeof props.value === 'undefined' || props.value === '' || !Array.isArray(props.options) || props.options.length === 0) return false
  // 传入值为数组
  let unMatch = false // 添加一个标志来判断是否有未匹配项
  values.value.forEach(item => {
    if (!props.options.some(v => v.value === item)) {
      unMatchArray.value.push(item)
      unMatch = true // 如果有未匹配项，将标志设置为true
    }
  })
  return unMatch // 返回标志的值
});

function handleArray(array: string[]) {
  if (array.length === 0) return ""
  return array.reduce((pre, cur) => {
    return pre + " " + cur
  })
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
