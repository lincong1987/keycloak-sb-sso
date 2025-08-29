import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbBreadcrumbItem 组件的 Props 类型
export interface FbBreadcrumbItemProps {
  /** 尺寸 */
  size?: string
  
  /** 分隔符 */
  separator?: string
  
  /** 链接地址 */
  href?: string
  
  /** 路由路径 */
  to?: string | object
  
  /** 是否替换当前路由 */
  replace?: boolean
  
  /** 是否追加 */
  append?: boolean
  
  /** 打开方式 */
  target?: string
  
  /** 图标 */
  icon?: string
  
  /** 标签 */
  label?: string
  
  /** 标题 */
  title?: string
  
  /** 颜色 */
  color?: string
  
  /** 下划线 */
  underline?: string
}

// 定义 FbBreadcrumbItem 组件的 Data 属性类型
export interface FbBreadcrumbItemData {
  /** 组件前缀 */
  prefix: string
  
  /** 内部分隔符 */
  mySeparator: string
  
  /** 内部下划线 */
  myUnderline: string
}

// 定义 FbBreadcrumbItem 组件的 Computed 属性类型
export interface FbBreadcrumbItemComputed {
  /** 链接类名 */
  getLinkClass: string[]
}

// 定义 FbBreadcrumbItem 组件的 Slots 类型
export interface FbBreadcrumbItemSlots {
  /** 默认插槽 */
  default: VNode[]
  
  /** 分隔符插槽 */
  separator: VNode[]
}

// 定义 FbBreadcrumbItem 组件实例类型
export interface FbBreadcrumbItem extends Vue, FbBreadcrumbItemProps, FbBreadcrumbItemData, FbBreadcrumbItemComputed {
  $slots: FbBreadcrumbItemSlots
}

// 定义 FbBreadcrumbItem 组件构造函数类型
export interface FbBreadcrumbItemConstructor extends VueConstructor<FbBreadcrumbItem> {}

// 导出 FbBreadcrumbItem 组件类型
export const FbBreadcrumbItem: FbBreadcrumbItemConstructor

// 默认导出
export default FbBreadcrumbItem