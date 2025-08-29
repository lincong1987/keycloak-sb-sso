import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义面包屑项的类型
export interface BreadcrumbItem {
  /** 标签 */
  label?: string
  
  /** 路由路径 */
  to?: string | object
  
  /** 链接地址 */
  href?: string
  
  /** 图标 */
  icon?: string
  
  /** 分隔符 */
  separator?: string
  
  /** 是否替换当前路由 */
  replace?: boolean
  
  /** 打开方式 */
  target?: string
  
  /** 标题 */
  title?: string
  
  /** 尺寸 */
  size?: string
  
  /** 颜色 */
  color?: string
  
  /** 下划线 */
  underline?: string
  
  /** 是否追加 */
  append?: boolean
}

// 定义 FbBreadcrumb 组件的 Props 类型
export interface FbBreadcrumbProps {
  /** 尺寸 */
  size?: string
  
  /** 分割线 */
  separator?: string
  
  /** 数据 */
  data?: BreadcrumbItem[]
  
  /** 路由 */
  routes?: object[]
  
  /** 颜色 */
  color?: string
  
  /** 下划线 */
  underline?: string
  
  /** 是否追加 */
  append?: boolean
}

// 定义 FbBreadcrumb 组件的 Data 属性类型
export interface FbBreadcrumbData {
  /** 组件前缀 */
  prefix: string
  
  /** 内部数据 */
  myData: BreadcrumbItem[]
}

// 定义 FbBreadcrumb 组件的 Slots 类型
export interface FbBreadcrumbSlots {
  /** 默认插槽 */
  default: VNode[]
}

// 定义 FbBreadcrumb 组件实例类型
export interface FbBreadcrumb extends Vue, FbBreadcrumbProps, FbBreadcrumbData {
  $slots: FbBreadcrumbSlots
}

// 定义 FbBreadcrumb 组件构造函数类型
export interface FbBreadcrumbConstructor extends VueConstructor<FbBreadcrumb> {}

// 导出 FbBreadcrumb 组件类型
export const FbBreadcrumb: FbBreadcrumbConstructor

// 默认导出
export default FbBreadcrumb