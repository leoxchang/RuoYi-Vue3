import { validateNull } from '@/utils/validate'

const keyName = 'truntao' + '-'

interface TagItem {
  title: string;
  name: string;
  path: string;
  query?: any;
  meta?: any;
  fullPath?: string;
  params?: any;
  [key: string]: any;
}

interface StoreObject {
  dataType: string;
  content: any;
  type?: boolean;
  datetime: number;
}

interface StoreParams {
  name: string;
  content?: any[];
  type?: boolean;
  debug?: boolean;
}

/**
 * 存储localStorage
 */
export const setStore = (params: StoreParams) => {
  let {
    name,
    content,
    type
  } = params
  name = keyName + name
  let tags: TagItem[] = []
  for (let row of content || []) {
    let tag: TagItem = {
      title: row.meta?.title || 'no-name',
      name: row.name || '',
      path: row.path || ''
    }
    tag.query = row.query
    tag.meta = row.meta
    tag.fullPath = row.fullPath
    tag.params = row.params
    tags.push(tag)
  }
  let obj: StoreObject = {
    dataType: typeof (tags),
    content: tags,
    type: type,
    datetime: new Date().getTime()
  }
  if (type) {
    window.sessionStorage.setItem(name, JSON.stringify(obj))
  } else {
    window.localStorage.setItem(name, JSON.stringify(obj))
  }
}

/**
 * 获取localStorage
 */
export const getStore = (params: StoreParams) => {
  let {
    name,
    debug
  } = params
  name = keyName + name
  let obj: any = {},
    content: any
  obj = window.sessionStorage.getItem(name)
  if (validateNull(obj)) obj = window.localStorage.getItem(name)
  if (validateNull(obj)) return
  try {
    obj = JSON.parse(obj)
  } catch {
    return obj
  }
  if (debug) {
    return obj
  }
  if (obj.dataType == 'string') {
    content = obj.content
  } else if (obj.dataType == 'number') {
    content = Number(obj.content)
  } else if (obj.dataType == 'boolean') {
    content = eval(obj.content)
  } else if (obj.dataType == 'object') {
    content = obj.content
  }
  return content
}

/**
 * 删除localStorage
 */
export const removeStore = (params: StoreParams) => {
  let {
    name,
    type
  } = params
  name = keyName + name
  if (type) {
    window.sessionStorage.removeItem(name)
  } else {
    window.localStorage.removeItem(name)
  }
}

interface StoreItem {
  name: string | null;
  content: any;
}

/**
 * 获取全部localStorage
 */
export const getAllStore = (params: StoreParams) => {
  let list: StoreItem[] = []
  let {
    type
  } = params
  if (type) {
    for (let i = 0; i <= window.sessionStorage.length; i++) {
      const key = window.sessionStorage.key(i)
      if (key) {
        list.push({
          name: key,
          content: getStore({
            name: key,
            type: true
          })
        })
      }
    }
  } else {
    for (let i = 0; i <= window.localStorage.length; i++) {
      const key = window.localStorage.key(i)
      if (key) {
        list.push({
          name: key,
          content: getStore({
            name: key
          })
        })
      }
    }
  }
  return list
}

/**
 * 清空全部localStorage
 */
export const clearStore = (params: StoreParams) => {
  let { type } = params
  if (type) {
    window.sessionStorage.clear()
  } else {
    window.localStorage.clear()
  }
}
