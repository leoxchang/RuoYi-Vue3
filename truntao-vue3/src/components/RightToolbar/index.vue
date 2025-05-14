<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top" v-if="search">
        <el-button circle icon="Search" @click="toggleSearch()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button circle icon="Refresh" @click="refresh()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns">
        <el-button circle icon="Menu" @click="showColumn()" v-if="showColumnsType == 'transfer'"/>
        <el-dropdown trigger="click" :hide-on-click="false" style="padding-left: 12px" v-if="showColumnsType == 'checkbox'">
          <el-button circle icon="Menu" />
          <template #dropdown>
            <el-dropdown-menu>
              <!-- 全选/反选 按钮 -->
              <el-dropdown-item>
                <el-checkbox :indeterminate="isIndeterminate" v-model="isChecked" @change="toggleCheckAll"> 列展示 </el-checkbox>
              </el-dropdown-item>
              <div class="check-line"></div>
              <template v-for="item in columns" :key="item.key">
                <el-dropdown-item>
                  <el-checkbox v-model="item.visible" @change="checkboxChange($event, item.label)" :label="item.label" />
                </el-dropdown-item>
              </template>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" v-model="open" append-to-body>
      <el-transfer
        :titles="['显示', '隐藏']"
        v-model="value"
        :data="columns"
        @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Column {
  key: number;
  label: string;
  visible: boolean;
}

interface Props {
  showSearch?: boolean;
  columns?: Column[];
  search?: boolean;
  showColumnsType?: 'transfer' | 'checkbox';
  gutter?: number;
}

const props = withDefaults(defineProps<Props>(), {
  showSearch: true,
  search: true,
  showColumnsType: 'checkbox',
  gutter: 10
})

const emits = defineEmits<{
  (e: 'update:showSearch', value: boolean): void;
  (e: 'queryTable'): void;
}>()

// 显隐数据
const value = ref<number[]>([])
// 弹出层标题
const title = ref("显示/隐藏")
// 是否显示弹出层
const open = ref(false)

const style = computed(() => {
  const ret: Record<string, string> = {};
  if (props.gutter) {
    ret.marginRight = `${props.gutter / 2}px`;
  }
  return ret;
})

// 是否全选/半选 状态
const isChecked = computed({
  get: () => props.columns.every(col => col.visible),
  set: () => {}
})
const isIndeterminate = computed(() => props.columns.some((col) => col.visible) && !isChecked.value)

// 搜索
function toggleSearch() {
  emits("update:showSearch", !props.showSearch)
}

// 刷新
function refresh() {
  emits("queryTable")
}

// 右侧列表元素变化
function dataChange(data: number[]) {
  for (let item in props.columns) {
    const key = props.columns[item].key
    props.columns[item].visible = !data.includes(key)
  }
}

// 打开显隐列dialog
function showColumn() {
  open.value = true
}

if (props.showColumnsType === 'transfer' && props.columns) {
  // 显隐列初始默认隐藏列
  for (let item in props.columns) {
    if (props.columns[item].visible === false) {
      value.value.push(parseInt(item))
    }
  }
}

// 勾选
function checkboxChange(event: boolean, label: string) {
  if (props.columns) {
    props.columns.filter(item => item.label === label)[0].visible = event;
  }
}
// 切换全选/反选
function toggleCheckAll() {
  const newValue = !isChecked.value
  props.columns.forEach((col) => (col.visible = newValue))
}
</script>

<style lang='scss' scoped>
:deep(.el-transfer__button) {
  border-radius: 50%;
  display: block;
  margin-left: 0px;
}
:deep(.el-transfer__button:first-child) {
  margin-bottom: 10px;
}
:deep(.el-dropdown-menu__item) {
  line-height: 30px;
  padding: 0 17px;
}
</style>
