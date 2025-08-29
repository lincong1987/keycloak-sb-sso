import { VNode } from 'vue'
import { FbUIComponent } from '@/fb-ui/types/component'

export type SpaceSize = 's' | 'm' | 'l' | 'xl' | number | string
export type SpaceDirection = 'horizontal' | 'vertical'
export type SpaceAlign = 'start' | 'end' | 'center' | 'baseline'

/** Space Component */
export declare class FbSpace extends FbUIComponent {
  /** 间隔尺寸 */
  size: SpaceSize

  /** 方向 */
  direction: SpaceDirection

  /** 对齐方式 */
  align: SpaceAlign
}