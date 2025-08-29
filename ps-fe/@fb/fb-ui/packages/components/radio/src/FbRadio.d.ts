import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbRadio 组件的 Props 类型
export interface FbRadioProps {
  /** 选中状态的值 */
  value?: string | number | boolean
  
  /** 单选框标签 */
  label?: string | number | boolean
  
  /** 是否禁用 */
  disabled?: boolean
  
  /** 是否只读 */
  readonly?: boolean
  
  /** 尺寸 */
  size?: string
}

// 定义 FbRadio 组件的 Data 属性类型
export interface FbRadioData {
  /** 组件前缀 */
  prefix: string
  
  /** 名称 */
  name: string
  
  /** 是否选中 */
  checked: boolean | string | number
  
  /** 内部值 */
  myValue: string | number | boolean
  
  /** 是否属于组 */
  group: boolean
}

// 定义 FbRadio 组件的 Computed 属性类型
export interface FbRadioComputed {
  /** 计算后的类名 */
  getClass: string[]
  
  /** 计算后的元素类名 */
  getElClass: string[]
}

// 定义 FbRadio 组件的 Slots 类型
export interface FbRadioSlots {
  /** 标签插槽 */
  label: VNode[]
}

// 定义 FbRadio 组件实例类型
export interface FbRadio extends Vue, FbRadioProps, FbRadioData, FbRadioComputed {
  $slots: FbRadioSlots
  
  /** 处理点击事件 */
  handleClick(event: Event): void
}

// 定义 FbRadio 组件构造函数类型
export interface FbRadioConstructor extends VueConstructor<FbRadio> {}

// 导出 FbRadio 组件类型
export const FbRadio: FbRadioConstructor

// 默认导出
export default FbRadio