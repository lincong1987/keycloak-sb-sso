import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbIcon 组件的 Props 类型
export interface FbIconProps {
  /** 图标名称 */
  name?: string
  
  /** 图标尺寸 */
  size?: string | number
  
  /** 图标颜色 */
  color?: string
  
  /** 是否旋转 */
  rotating?: boolean
  
  /** 是否加粗 */
  bold?: boolean
  
  /** 字体粗细 */
  weight?: string | number
  
  /** 右边距 */
  mr?: string | number
  
  /** 左边距 */
  ml?: string | number
  
  /** 垂直对齐 */
  valign?: string | number
}

// 定义 FbIcon 组件的 Data 属性类型
export interface FbIconData {
  // 组件前缀
}

// 定义 FbIcon 组件的 Computed 属性类型
export interface FbIconComputed {
  /** 计算后的图标尺寸样式 */
  mySize: string
}

// 定义 FbIcon 组件的 Slots 类型
export interface FbIconSlots {
  /** 默认插槽 */
  default: VNode[]
}

// 定义 FbIcon 组件实例类型
export interface FbIcon extends Vue, FbIconProps, FbIconData, FbIconComputed {
  $slots: FbIconSlots
  
  /** 移除图标前缀 */
  removeIconPrefix(val: string): string
  
  /** 判断是否包含 px */
  hasPx(val: string | number): boolean
  
  /** 处理点击事件 */
  handleClick(event: Event): void
  
  /** 处理鼠标进入事件 */
  handleMouseEnter(event: Event): void
  
  /** 处理鼠标离开事件 */
  handleMouseLeave(event: Event): void
}

// 定义 FbIcon 组件构造函数类型
export interface FbIconConstructor extends VueConstructor<FbIcon> {}

// 导出 FbIcon 组件类型
export const FbIcon: FbIconConstructor

// 默认导出
export default FbIcon