import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbButton 组件的 Props 类型
export interface FbButtonProps {
  /** 按钮的值 */
  value?: string
  
  /** 复制的文本 */
  copy?: string
  
  /** 按钮的 name 属性 */
  name?: string
  
  /** 按钮类型 */
  type?: 'default' | 'link' | string
  
  /** 按钮尺寸 */
  size?: 's' | 'm' | 'l' | string
  
  /** 是否 100% 宽度 */
  long?: boolean
  
  /** hover 状态 */
  hover?: boolean
  
  /** 按钮元素的类型 */
  elType?: 'button' | 'submit' | 'reset' | 'menu'
  
  /** 是否显示加载状态 */
  loading?: boolean
  
  /** 是否为危险按钮 */
  danger?: boolean
  
  /** 是否为编辑器按钮 */
  editor?: boolean
  
  /** 是否为警告按钮 */
  warning?: boolean
  
  /** 是否为成功按钮 */
  success?: boolean
  
  /** 是否禁用 */
  disabled?: boolean
  
  /** 是否为清爽按钮 */
  plain?: boolean
  
  /** 是否自动获取焦点 */
  autofocus?: boolean
  
  /** 是否为圆角按钮 */
  round?: boolean
  
  /** 是否为圆形按钮 */
  circle?: boolean
  
  /** 图标名称 */
  icon?: string
  
  /** 追加图标名称 */
  appendIcon?: string
}

// 定义 FbButton 组件的 Data 属性类型
export interface FbButtonData {
  /** 鼠标按下状态 */
  mousedown: boolean
  
  /** 复制动画状态 */
  copying: boolean
  
  /** 内部图标 */
  myIcon: string
}

// 定义 FbButton 组件的 Slots 类型
export interface FbButtonSlots {
  /** 默认插槽 */
  default: VNode[]
}

// 定义 FbButton 组件实例类型
export interface FbButton extends Vue, FbButtonProps, FbButtonData {
  $slots: FbButtonSlots
  
  /** 处理点击事件 */
  handleClick(event: Event): void
  
  /** 执行复制操作 */
  doCopy(): void
  
  /** 显示复制动画 */
  showCopyAnimation(): void
}

// 定义 FbButton 组件构造函数类型
export interface FbButtonConstructor extends VueConstructor<FbButton> {}

// 导出 FbButton 组件类型
export const FbButton: FbButtonConstructor

// 默认导出
export default FbButton