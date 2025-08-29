import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbCheckbox 组件的 Props 类型
export interface FbCheckboxProps {
  /** 选中状态的值 */
  value?: string | number | boolean
  
  /** 复选框标签 */
  label?: string | number | boolean
  
  /** 是否禁用 */
  disabled?: boolean
  
  /** 是否只读 */
  readonly?: boolean
  
  /** 节点数据 */
  node?: object | null
  
  /** 索引 */
  index?: number
  
  /** 尺寸 */
  size?: string
}

// 定义 FbCheckbox 组件的 Data 属性类型
export interface FbCheckboxData {
  /** 组件前缀 */
  prefix: string
  
  /** 名称 */
  name: string
  
  /** 是否选中 */
  checked: boolean | string | number
  
  /** 是否属于组 */
  group: boolean
}

// 定义 FbCheckbox 组件的 Computed 属性类型
export interface FbCheckboxComputed {
  /** 计算后的类名 */
  getClass: string[]
  
  /** 计算后的元素类名 */
  getElClass: string[]
}

// 定义 FbCheckbox 组件的 Slots 类型
export interface FbCheckboxSlots {
  /** 默认插槽 */
  default: VNode[]
  
  /** 复选框插槽 */
  checkbox: (props: { 
    name: string; 
    label: string | number | boolean; 
    value: string | number | boolean; 
    disabled: boolean; 
    readonly: boolean; 
    checked: boolean | string | number; 
    index: number; 
    node: object | null 
  }) => VNode[]
  
  /** 标签插槽 */
  label: (props: { 
    name: string; 
    label: string | number | boolean; 
    value: string | number | boolean; 
    disabled: boolean; 
    readonly: boolean; 
    checked: boolean | string | number; 
    index: number; 
    node: object | null 
  }) => VNode[]
}

// 定义 FbCheckbox 组件实例类型
export interface FbCheckbox extends Vue, FbCheckboxProps, FbCheckboxData, FbCheckboxComputed {
  $slots: FbCheckboxSlots
  
  /** 处理点击事件 */
  handleClick(event: Event): void
}

// 定义 FbCheckbox 组件构造函数类型
export interface FbCheckboxConstructor extends VueConstructor<FbCheckbox> {}

// 导出 FbCheckbox 组件类型
export const FbCheckbox: FbCheckboxConstructor

// 默认导出
export default FbCheckbox