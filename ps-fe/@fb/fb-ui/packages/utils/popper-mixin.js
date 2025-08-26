import VuePopper from './vue-popper'

const PopperMixin = {
	props: {
		placement: {
			type: String,
			default: 'bottom-start',
		},
		appendToBody: VuePopper.props.appendToBody,
		visibleArrow: { // 影响弹窗初始化位置
			type: Boolean,
			default: false,
		},
		arrowOffset: VuePopper.props.arrowOffset,
		offset: VuePopper.props.offset,
		boundariesPadding: VuePopper.props.boundariesPadding,
		popperOptions: VuePopper.props.popperOptions,
	},
	methods: VuePopper.methods,
	data: VuePopper.data,
	beforeDestroy: VuePopper.beforeDestroy,
}

export default PopperMixin
