import { Vue, VueConstructor } from 'vue/types/vue'
import { VNode } from 'vue'

// 定义 FbAlert 组件的 Props 类型
export interface FbAlertProps {
  /** 类型 */
  type?: 'primary' | 'alert' | 'info' | 'confirm' | 'success' | 'error' | 'danger' | 'warning' | 'loading'
  
  /** 消息 */
  message?: string | number
  
  /** 描述 */
  description?: string | number
  
  /** 是否可以关闭 */
  closable?: boolean
  
  /** 是否显示 */
  show?: boolean
  
  /** 是否有边框 */
  bordered?: boolean
  
  /** 自定义图标 */
  icon?: string
}

// 定义 FbAlert 组件的 Data 属性类型
export interface FbAlertData {
  /** 图标映射 */
  icons: Record<string, string>
  
  /** 内部显示状态 */
  myShow: boolean
}

// 定义 FbAlert 组件的 Computed 属性类型
export interface FbAlertComputed {
  /** 组件类名 */
  getClass: string[]
  
  /** 当前类型对应的图标 */
  myType: string
  
  /** 最终使用的图标 */
  myIcon: string
  
  /** 是否有描述 */
  hasDescription: boolean
}

// 定义 FbAlert 组件的 Slots 类型
export interface FbAlertSlots {
  /** 默认插槽 */
  default: VNode[]
  
  /** 消息插槽 */
  message: VNode[]
  
  /** 描述插槽 */
  description: VNode[]
  
  /** 操作区域插槽 */
  actions: VNode[]
}

// 定义 FbAlert 组件实例类型
export interface FbAlert extends Vue, FbAlertProps, FbAlertData, FbAlertComputed {
  $slots: FbAlertSlots
}

// 定义 FbAlert 组件构造函数类型
export interface FbAlertConstructor extends VueConstructor<FbAlert> {}

// 导出 FbAlert 组件类型
export const FbAlert: FbAlertConstructor

// 默认导出
export default FbAlert