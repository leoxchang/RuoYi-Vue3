/**
 * v-hasPermi 操作权限处理
 */

import useUserStore from '@/store/modules/user'

// 定义指令绑定参数接口
interface DirectiveBinding {
  value: string[];
  [key: string]: any;
}

// 定义虚拟节点接口
interface VNode {
  [key: string]: any;
}

export default {
  mounted(el: HTMLElement, binding: DirectiveBinding, vnode: VNode) {
    const { value } = binding
    const all_permission = "*:*:*";
    const permissions = useUserStore().permissions

    if (value && value instanceof Array && value.length > 0) {
      const permissionFlag = value

      const hasPermissions = permissions.some(permission => {
        return all_permission === permission || permissionFlag.includes(permission)
      })

      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`请设置操作权限标签值`)
    }
  }
}