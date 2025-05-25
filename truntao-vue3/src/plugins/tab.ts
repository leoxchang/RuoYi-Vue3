import useTagsViewStore from '@/store/modules/tagsView'
import router from '@/router'
import {RouteLocationNormalized, RouteRecordNormalized, NavigationFailure} from 'vue-router'

// 定义路由对象接口
interface RouteObject {
  name?: string
  path: string
  query?: Record<string, any>
  fullPath?: string
  meta?: {
    title?: string
    [key: string]: any
  }
}

// 定义标签视图接口
interface TagView {
  visitedViews: RouteObject[]
}

// 定义参数接口
interface RouteParams {
  [key: string]: any
}

export default {
  // 刷新当前tab页签
  refreshPage(obj?: RouteObject): Promise<void> {
    const {path, query, matched} = router.currentRoute.value;
    if (obj === undefined) {
      matched.forEach((m: RouteRecordNormalized) => {
        if (m.components && m.components.default && (m.components.default as any).name) {
          if (!['Layout', 'ParentView'].includes((m.components.default as any).name)) {
            obj = {name: (m.components.default as any).name, path: path, query: query};
          }
        }
      });
    }
    return useTagsViewStore().delCachedView(obj!).then(() => {
      const {path, query} = obj!
      router.replace({
        path: '/redirect' + path,
        query: query
      })
    })
  },
  // 关闭当前tab页签，打开新页签
  closeOpenPage(obj?: RouteObject): Promise<NavigationFailure | void> | undefined {
    useTagsViewStore().delView(router.currentRoute.value);
    if (obj !== undefined) {
      return router.push(obj);
    }
  },
  // 关闭指定tab页签
  closePage(obj?: RouteObject): Promise<TagView | RouteLocationNormalized> {
    if (obj === undefined) {
      return useTagsViewStore().delView(router.currentRoute.value).then(({visitedViews}: TagView) => {
        const latestView = visitedViews.slice(-1)[0]
        if (latestView) {
          return router.push(latestView.fullPath!)
        }
        return router.push('/');
      });
    }
    return useTagsViewStore().delView(obj);
  },
  // 关闭所有tab页签
  closeAllPage(): Promise<void> {
    return useTagsViewStore().delAllViews();
  },
  // 关闭左侧tab页签
  closeLeftPage(obj?: RouteObject): Promise<void> {
    return useTagsViewStore().delLeftTags(obj || router.currentRoute.value);
  },
  // 关闭右侧tab页签
  closeRightPage(obj?: RouteObject): Promise<void> {
    return useTagsViewStore().delRightTags(obj || router.currentRoute.value);
  },
  // 关闭其他tab页签
  closeOtherPage(obj?: RouteObject): Promise<void> {
    return useTagsViewStore().delOthersViews(obj || router.currentRoute.value);
  },
  // 打开tab页签
  openPage(title: string, url: string, params?: RouteParams): Promise<NavigationFailure | void> {
    const obj: RouteObject = {path: url, meta: {title: title}}
    useTagsViewStore().addView(obj)
    return router.push({path: url, query: params})
  },
  // 修改tab页签
  updatePage(obj: RouteObject): Promise<void> {
    return useTagsViewStore().updateVisitedView(obj);
  }
}
