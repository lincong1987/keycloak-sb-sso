import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbText 组件的 Props 类型
export interface FbTextProps {
  /** 文本大小 */
  size?: string | number
  
  /** 文本类型 */
  type?: string
  
  /** 渐变背景 */
  gradient?: string
  
  /** 显示模式 */
  display?: string
  
  /** 内联样式 */
  inline?: boolean
  
  /** 定位方式 */
  position?: string
  
  /** 文本颜色 */
  color?: string
  
  /** 背景 */
  background?: string
  
  /** 文本对齐方式 */
  textAlign?: string
  
  /** 布局方式 */
  layout?: string
  
  /** 字体粗细 */
  fontWeight?: string | number
  
  /** 是否显示省略号 */
  ellipsis?: boolean
  
  /** 字体族 */
  family?: string
  
  /** 行高 */
  lineHeight?: string | number
  
  /** 字体样式 */
  fontStyle?: string
  
  /** 文本装饰 */
  textDecoration?: string
  
  /** 文本缩进 */
  indent?: string | number
  
  /** 外边距 */
  margin?: string | number
  
  /** 内边距 */
  padding?: string | number
  
  /** 过滤器 */
  filter?: string
  
  /** 排序 */
  order?: number
  
  /** 边框 */
  border?: string
  
  /** 溢出处理 */
  overflow?: string
  
  /** 盒模型 */
  boxSizing?: string
  
  /** 垂直对齐 */
  verticalAlign?: string
  
  /** 弹性布局 */
  flex?: string | number
  
  /** 盒阴影 */
  boxShadow?: string
  
  /** 自身对齐方式 */
  justifySelf?: string
  
  /** 鼠标指针样式 */
  cursor?: string
}

// 定义 FbText 组件的 Data 属性类型
export interface FbTextData {
  // 组件前缀
}

// 定义 FbText 组件的 Computed 属性类型
export interface FbTextComputed {
  /** 计算后的类名 */
  getClass: string[]
  
  /** 计算后的样式 */
  getStyle: object
}

// 定义 FbText 组件的 Slots 类型
export interface FbTextSlots {
  /** 默认插槽 */
  default: VNode[]
}

// 定义 FbText 组件实例类型
export interface FbText extends Vue, FbTextProps, FbTextData, FbTextComputed {
  $slots: FbTextSlots
}

// 定义 FbText 组件构造函数类型
export interface FbTextConstructor extends VueConstructor<FbText> {}

// 导出 FbText 组件类型
export const FbText: FbTextConstructor

// 默认导出
export default FbText