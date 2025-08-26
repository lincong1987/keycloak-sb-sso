// vue-popper 根据 https://popper.js.org/ 处理而来
// 整体改造与 el-tooltip 一致
import Popper from './vue-popper'
import {debounce} from 'lodash-es'
import {addClass, removeClass, on, off} from './dom'
import {generateId} from './utils'
import {prefix} from '../../src/config'
import Vue from 'vue'

export default {
	name: 'FbDropDown',

	mixins: [Popper],

	props: {

		openDelay: {
			type: Number,
			default: 0,
		},
		manual: Boolean,
		effect: {
			type: String,
			default: 'dark',
		},
		arrowOffset: {
			type: Number,
			default: 0,
		},
		popperClass: String,
		content: String,
		visibleArrow: {
			default: false,
		},
		transition: {
			type: String,
			default: 'fade',
		},
		popperOptions: {
			default() {
				return {
					boundariesPadding: 10,
					gpuAcceleration: false,
				}
			},
		},
		enterable: {
			type: Boolean,
			default: true,
		},
		hideAfter: {
			type: Number,
			default: 0,
		},
		tabindex: {
			type: Number,
			default: 0,
		},
		tipStyle: {
			type: [String, Object, Array],
			default() {
				return [{
					border: 'none',
					padding: 0,
					margin: 0,
					background: 'transparent',
				}]
			},
		},
		focusClass: {
			type: String,
			default: '',
		},
	},

	data() {
		return {
			dropdownId: `${prefix}-dropdown-${generateId()}`,
			timeoutPending: null,
			focusing: false,
			expectedState: true,
		}
	},
	beforeCreate() {
		if (this.$isServer) return

		this.popperVM = new Vue({
			data: {node: ''},
			render(h) {
				return this.node
			},
		}).$mount()

		this.debounceClose = debounce(() => this.handleClosePopper(), 200)
	},

	render(h) {
		if (this.popperVM) {
			this.popperVM.node = h(
				'transition', {
					attrs: {
						name: this.transition,
					},
					on: {
						afterLeave: this.doDestroy,
					},
				}, [
					h('div', {
						ref: 'popper',
						attrs: {
							role: 'dropdown',
							// id: this.dropdownId,
							'aria-hidden': !this.showPopper ? 'true' : 'false',
						},
						directives: [
							{
								name: 'show',
								value: this.showPopper,
							},
						],
						class: [
							`${prefix}-dropdown__popper`,
							'is-' + this.effect,
							this.popperClass,
						],
						style: this.tipStyle,
					}, [this.$slots.content || this.content]),
				],
			)
		}

		const firstElement = this.getFirstElement()
		if (!firstElement) return null

		const data = firstElement.data = firstElement.data || {}
		data.staticClass = this.addDropdownClass(data.staticClass)

		return firstElement
	},

	mounted() {

		this.referenceElm = this.$el
		if (this.$el.nodeType === 1) {
			this.$el.setAttribute('aria-describedby', this.dropdownId)
			this.$el.setAttribute('tabindex', this.tabindex)
		}
		// fix issue https://github.com/ElemeFE/element/issues/14424
		if (this.value && this.popperVM) {
			this.popperVM.$nextTick(() => {
				if (this.value) {
					this.updatePopper()
				}
			})
		}
	},
	watch: {
		focusing(val) {
			if (val) {
				addClass(this.referenceElm, this.focusClass)
			} else {
				removeClass(this.referenceElm, this.focusClass)
			}
		},
		expectedState(val) {
			console.log('expectedState', val)
		},
	},
	methods: {
		show() {
			this.setExpectedState(true)
			this.handleShowPopper()
		},

		hide() {
			this.setExpectedState(false)
			this.debounceClose()
		},
		toggle() {
			if (this.getExpectedState() === false) {
				this.hide()
				this.handleClosePopper()
			} else {
				this.show()
			}
		},
		handleFocus() {
			this.focusing = true
			this.show()
		},
		handleBlur() {
			this.focusing = false
			this.hide()
		},
		removeFocusing() {
			this.focusing = false
		},

		addDropdownClass(prev) {
			if (!prev) {
				return `${prefix}-dropdown`
			} else {
				return `${prefix}-dropdown ` +
					prev.replace(`${prefix}-dropdown`, '')
			}
		},

		handleShowPopper() {
			if (!this.expectedState || this.manual) return
			clearTimeout(this.timeout)
			this.timeout = setTimeout(() => {
				this.showPopper = true
			}, this.openDelay)

			if (this.hideAfter > 0) {
				this.timeoutPending = setTimeout(() => {
					this.showPopper = false
				}, this.hideAfter)
			}
		},

		handleClosePopper() {
			if (this.enterable && this.expectedState || this.manual) return
			clearTimeout(this.timeout)

			if (this.timeoutPending) {
				clearTimeout(this.timeoutPending)
			}
			this.showPopper = false
		},

		setExpectedState(expectedState) {
			if (expectedState === false) {
				clearTimeout(this.timeoutPending)
			}
			this.expectedState = expectedState
		},

		getExpectedState() {
			return this.expectedState
		},

		getFirstElement() {
			const slots = this.$slots.default
			if (!Array.isArray(slots)) return null
			let element = null
			for (let index = 0; index < slots.length; index++) {
				if (slots[index] && slots[index].tag) {
					element = slots[index]
				}

			}
			return element
		},
	},

	beforeDestroy() {
		this.popperVM && this.popperVM.$destroy()
	},

	destroyed() {
		const reference = this.referenceElm
		if (reference.nodeType === 1) {
			off(reference, 'mouseenter', this.show)
			off(reference, 'mouseleave', this.hide)
			off(reference, 'focus', this.handleFocus)
			off(reference, 'blur', this.handleBlur)
			off(reference, 'click', this.removeFocusing)
		}
	},
}
