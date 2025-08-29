import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbTextarea 组件的 Props 类型
export interface FbTextareaProps {
  /** 值 */
  value?: number | string
  
  /** 类型 */
  type?: string
  
  /** 尺寸 */
  size?: string
  
  /** 占位符 */
  placeholder?: string
  
  /** 是否禁用 */
  disabled?: boolean
  
  /** 是否只读 */
  readonly?: boolean
  
  /** 是否可清除 */
  clearable?: boolean
  
  /** 最大长度 */
  maxlength?: number | string
  
  /** 图标 */
  icon?: string
  
  /** 行数 */
  rows?: number | string
  
  /** 宽度 */
  width?: string | number
  
  /** 名称 */
  name?: string
  
  /** 自动完成 */
  autocomplete?: string
  
  /** 是否圆角 */
  round?: boolean
  
  /** 前置内容 */
  prepend?: string
  
  /** 后置内容 */
  append?: string
}

// 定义 FbTextarea 组件的 Data 属性类型
export interface FbTextareaData {
  /** 组件前缀 */
  prefix: string
  
  /** 当前值 */
  currentValue: string
  
  /** 是否密码框 */
  password: boolean
  
  /** 是否显示密码 */
  showPassword: boolean
  
  /** 当前类型 */
  currentType: string
  
  /** 是否悬停 */
  hovering: boolean
  
  /** 是否有前置按钮 */
  prependButton: boolean
  
  /** 是否有后置按钮 */
  appendButton: boolean
  
  /** 表单项 */
  fbFormItem: any
  
  /** 插槽是否就绪 */
  slotReady: boolean
}

// 定义 FbTextarea 组件的 Computed 属性类型
export interface FbTextareaComputed {
  /** 宽度 */
  myWidth: string
  
  /** 是否有前置 */
  hasPrepend: boolean
  
  /** 是否有后置 */
  hasAppend: boolean
}

// 定义 FbTextarea 组件的 Slots 类型
export interface FbTextareaSlots {
  /** 默认插槽 */
  default: VNode[]
  
  /** 前置插槽 */
  prepend: VNode[]
  
  /** 前置按钮插槽 */
  'prepend-button': VNode[]
  
  /** 后置插槽 */
  append: VNode[]
  
  /** 后置按钮插槽 */
  'append-button': VNode[]
}

// 定义 FbTextarea 组件实例类型
export interface FbTextarea extends Vue, FbTextareaProps, FbTextareaData, FbTextareaComputed {
  $slots: FbTextareaSlots
  
  /** 处理删除 */
  handleDelete(event: Event): void
  
  /** 处理图标点击 */
  handleIconClick(event: Event): void
  
  /** 处理回车 */
  handleEnter(event: Event): void
  
  /** 处理聚焦 */
  handleFocus(event: Event): void
  
  /** 处理失焦 */
  handleBlur(event: Event): void
  
  /** 处理输入 */
  handleInput(event: Event): void
  
  /** 切换输入类型 */
  changeInputType(): void
  
  /** 设置当前值 */
  setCurrentValue(value: string): void
  
  /** 点击处理 */
  click(e: Event): void
  
  /** 获取长度 */
  getLength(): number
  
  /** 处理清除 */
  handleClear(): void
}

// 定义 FbTextarea 组件构造函数类型
export interface FbTextareaConstructor extends VueConstructor<FbTextarea> {}

// 导出 FbTextarea 组件类型
export const FbTextarea: FbTextareaConstructor

// 默认导出
export default FbTextarea