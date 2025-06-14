export interface Settings {
  title: string;
  theme?: string;
  sideTheme: string;
  showSettings: boolean;
  topNav: boolean;
  tagsView: boolean;
  tagsIcon: boolean;
  fixedHeader: boolean;
  sidebarLogo: boolean;
  dynamicTitle: boolean;
  footerVisible: boolean;
  footerContent: string;
  isDark: boolean;
}

const settings: Settings = {
  /**
   * 网页标题
   */
  title: import.meta.env.VITE_APP_TITLE,
  /**
   * 侧边栏主题 深色主题theme-dark，浅色主题theme-light
   */
  sideTheme: 'theme-dark',
  /**
   * 是否系统布局配置
   */
  showSettings: true,

  /**
   * 是否显示顶部导航
   */
  topNav: false,

  /**
   * 是否显示 tagsView
   */
  tagsView: true,

  /**Add commentMore actions
   * 显示页签图标
   */
  tagsIcon: false,

  /**
   * 是否固定头部
   */
  fixedHeader: false,

  /**
   * 是否显示logo
   */
  sidebarLogo: true,

  /**
   * 是否显示动态标题
   */
  dynamicTitle: false,
  /**Add commentMore actions
   * 是否显示底部版权
   */
  footerVisible: false,

  /**
   * 底部版权文本内容
   */
  footerContent: 'Copyright © 2018-2025 Truntao. All Rights Reserved.',
  isDark: false
}

export default settings;
