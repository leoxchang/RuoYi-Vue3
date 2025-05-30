import { getStore, setStore } from '@/utils/tag-store'
import { defineStore } from 'pinia'

interface TagMeta {
  title?: string
  affix?: boolean
  noCache?: boolean
  link?: string
  [key: string]: any
}

interface TagView {
  name: string
  path: string
  meta: TagMeta
  [key: string]: any
}

interface TagsViewState {
  visitedViews: TagView[]
  cachedViews: string[]
  iframeViews: TagView[]
}

const useTagsViewStore = defineStore('tags-view', {
  state: (): TagsViewState => ({
    visitedViews: getStore({ name: 'visitedViews' }) || [],
    cachedViews: [],
    iframeViews: []
  }),
  actions: {
    addView(view: TagView) {
      this.addVisitedView(view)
      this.addCachedView(view)
    },
    addIframeView(view: TagView) {
      if (this.iframeViews.some(v => v.path === view.path)) return
      this.iframeViews.push(
        Object.assign({}, view, {
          title: view.meta.title || 'no-name'
        })
      )
    },
    addVisitedView(view: TagView) {
      if (this.visitedViews.some(v => v.path === view.path)) return
      this.visitedViews.push(
        Object.assign({}, view, {
          title: view.meta.title || 'no-name'
        })
      )
      setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
    },
    addCachedView(view: TagView) {
      if (this.cachedViews.includes(view.name)) return
      if (!view.meta.noCache) {
        this.cachedViews.push(view.name)
      }
    },
    delView(view: TagView) {
      return new Promise<{ visitedViews: TagView[]; cachedViews: string[] }>(resolve => {
        this.delVisitedView(view)
        this.delCachedView(view)
        resolve({
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delVisitedView(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        for (const [i, v] of this.visitedViews.entries()) {
          if (v.path === view.path) {
            this.visitedViews.splice(i, 1)
            break
          }
        }
        this.iframeViews = this.iframeViews.filter(item => item.path !== view.path)
        resolve([...this.visitedViews])
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delIframeView(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        this.iframeViews = this.iframeViews.filter(item => item.path !== view.path)
        resolve([...this.iframeViews])
      })
    },
    delCachedView(view: TagView) {
      return new Promise<string[]>(resolve => {
        const index = this.cachedViews.indexOf(view.name)
        index > -1 && this.cachedViews.splice(index, 1)
        resolve([...this.cachedViews])
      })
    },
    delOthersViews(view: TagView) {
      return new Promise<{ visitedViews: TagView[]; cachedViews: string[] }>(resolve => {
        this.delOthersVisitedViews(view)
        this.delOthersCachedViews(view)
        resolve({
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delOthersVisitedViews(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        this.visitedViews = this.visitedViews.filter(v => {
          return v.meta.affix || v.path === view.path
        })
        this.iframeViews = this.iframeViews.filter(item => item.path === view.path)
        resolve([...this.visitedViews])
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delOthersCachedViews(view: TagView) {
      return new Promise<string[]>(resolve => {
        const index = this.cachedViews.indexOf(view.name)
        if (index > -1) {
          this.cachedViews = this.cachedViews.slice(index, index + 1)
        } else {
          this.cachedViews = []
        }
        resolve([...this.cachedViews])
      })
    },
    delAllViews(view: TagView) {
      return new Promise<{ visitedViews: TagView[]; cachedViews: string[] }>(resolve => {
        this.delAllVisitedViews(view)
        this.delAllCachedViews(view)
        resolve({
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delAllVisitedViews(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        const affixTags = this.visitedViews.filter(tag => tag.meta.affix)
        this.visitedViews = affixTags
        this.iframeViews = []
        resolve([...this.visitedViews])
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delAllCachedViews(view: TagView) {
      return new Promise<string[]>(resolve => {
        this.cachedViews = []
        resolve([...this.cachedViews])
      })
    },
    updateVisitedView(view: TagView) {
      for (let v of this.visitedViews) {
        if (v.path === view.path) {
          v = Object.assign(v, view)
          break
        }
      }
      setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
    },
    delRightTags(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        const index = this.visitedViews.findIndex(v => v.path === view.path)
        if (index === -1) {
          return
        }
        this.visitedViews = this.visitedViews.filter((item, idx) => {
          if (idx <= index || (item.meta && item.meta.affix)) {
            return true
          }
          const i = this.cachedViews.indexOf(item.name)
          if (i > -1) {
            this.cachedViews.splice(i, 1)
          }
          if(item.meta.link) {
            const fi = this.iframeViews.findIndex(v => v.path === item.path)
            this.iframeViews.splice(fi, 1)
          }
          return false
        })
        resolve([...this.visitedViews])
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    },
    delLeftTags(view: TagView) {
      return new Promise<TagView[]>(resolve => {
        const index = this.visitedViews.findIndex(v => v.path === view.path)
        if (index === -1) {
          return
        }
        this.visitedViews = this.visitedViews.filter((item, idx) => {
          if (idx >= index || (item.meta && item.meta.affix)) {
            return true
          }
          const i = this.cachedViews.indexOf(item.name)
          if (i > -1) {
            this.cachedViews.splice(i, 1)
          }
          if(item.meta.link) {
            const fi = this.iframeViews.findIndex(v => v.path === item.path)
            this.iframeViews.splice(fi, 1)
          }
          return false
        })
        resolve([...this.visitedViews])
        setStore({ name: 'visitedViews', content: this.visitedViews, type: true })
      })
    }
  }
})

export default useTagsViewStore 