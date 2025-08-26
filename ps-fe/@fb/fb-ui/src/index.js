/*!
 * index
 * (c) 2020 lincong1987
 */
// import 'vue-draggable-resizable/dist/VueDraggableResizable.css' // Commented out due to webpack import issue
import '../packages/theme/pc3/src/index.less'
import pkg from '../package.json'
import { keyBy, camelCase } from 'lodash-es'
import _propsUtil from '../packages/utils/props-util'
import componentList from '../packages/components/components'
import loadingBar from '../packages/components/loading-bar'
import message from '../packages/components/message'
import msgbox from '../packages/components/message-box'
import loading from '../packages/components/spin'
import notification from '../packages/components/notification'
import { prefix, defaults } from './config'
import { colors as pc3 } from '../packages/theme/pc3/src/common/colors'
import transitions from '../packages/transitions/transitions'

import { glyphs } from '../packages/theme/pc3/src/fonts/iconfont.json'
let icons = {}

glyphs.map(n => {
	icons[camelCase(n.font_class.toLowerCase())] = n.font_class
})

export const theme = {
	pc3,
}

//console.log(`use fb-ui ${pkg.version}`)
console.log(
	`%c fb-ui v${pkg.version}  %c docs: http://10.10.0.36/fb/fb-docs/#/fb-ui/ `,
	`padding: 5px 0; background: #0284FE; color: #fff; border-radius: 4px 0 0 4px;`,
	`padding: 5px 0; background: #fff; color: #0284FE; border-radius: 0 4px 4px 0;`)

/**
 * 安装 FbUI
 * @param Vue
 * @param opts
 */
export const install = function (Vue, opts = {}) {

	([
		mixinVue,
		setDefaultProps,
		registerComponents,
		setPrototype,
	]).map((fn) => {
		if (typeof opts[fn.name] !== 'undefined') {
			opts[fn.name](Vue, opts)
		} else {
			fn(Vue, opts)
		}
	})

}

/**
 * 设置组件默认参数
 * @param Vue
 * @param opts
 */
export function setDefaultProps (Vue, opts) {
	window.FbUI_defaultProps = Vue.prototype.defaultProps =
		Object.assign({}, defaults, opts.defaultProps || {})
}

/**
 * 混合 mixinVue
 * @param Vue
 * @param opts
 */
export function mixinVue (Vue, opts) {
	Vue.mixin({
		data () {
			return {
				icons,
				colors: theme[opts.theme || 'pc3'],
				transitions,
				prefix,
			}
		},
	})
}

export function registerComponents (Vue) {
	componentList.map(component => {
		component.componentName = component.name

		// _propsUtil.initDefaultProps(component.props, window.FbUI_defaultProps[component.name] || {})
		// 目前只修改，FbSimpleTable，其他组件需要更详细的测试
		if (component.name === 'FbSimpleTable' ||
			component.name === 'FbDialog' ||
			component.name === 'FbNumber' ||
			component.name === 'FbFlop' ||
			component.name === 'FbTree' ||
			component.name === 'FbUpload' ||
			component.name === 'FbProperty' ||
			component.name === 'FbPropertyItem' ||
			component.name === 'FbUpload' ||
			component.name === 'FbDatepicker') {
			_propsUtil.initDefaultProps(component.props, window.FbUI_defaultProps[component.name] || {})
		}

		Vue.component(component.name, component)
	})
}

export function setPrototype (Vue) {

	Vue.prototype.$loadingBar = loadingBar
	Vue.prototype.$loading = loading
	Vue.prototype.$message = message
	Vue.prototype.$msgbox = msgbox
	Vue.prototype.$alert = msgbox.alert
	Vue.prototype.$confirm = msgbox.confirm
	Vue.prototype.$notification = notification

}

/* istanbul ignore if */
if (typeof window !== 'undefined' && window.Vue) {
	console.log('Auto install fb-ui')
	install(window.Vue)
}

export default {
	install,
	theme,
	...componentList,
}

