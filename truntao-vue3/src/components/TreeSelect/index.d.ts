import type { PropType,DefineComponent } from 'vue'

export interface TreeSelectProps {
  /* 配置项 */
  objMap: {
    value: string
    label: string
    children: string
  }
  /* 自动收起 */
  accordion: boolean
  /**当前双向数据绑定的值 */
  value: string | number
  /**当前的数据 */
  options: Array<Record<string, any>>
  /**输入框内部的文字 */
  placeholder: string
}

export const treeSelectProps = {
  objMap: {
    type: Object as PropType<TreeSelectProps['objMap']>,
    default: () => ({
      value: 'id',
      label: 'label',
      children: 'children'
    })
  },
  accordion: {
    type: Boolean,
    default: false
  },
  value: {
    type: [String, Number] as PropType<TreeSelectProps['value']>,
    default: ''
  },
  options: {
    type: Array as PropType<TreeSelectProps['options']>,
    default: () => []
  },
  placeholder: {
    type: String,
    default: ''
  }
}

declare const TreeSelect: DefineComponent<TreeSelectProps>

// export default TreeSelect
