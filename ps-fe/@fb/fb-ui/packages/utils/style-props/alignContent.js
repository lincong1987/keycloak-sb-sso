/*!
 * alignContent
 * (c) 2024 lincong1987
 */


export const props = {

	// alignContent = ['center','flex-start','flex-end','space-around','space-between','stretch']
	alignContent: {
		type: String,
		default: undefined,
	},
	acCenter: {
		type: Boolean,
		default: false,
	},
	acSpaceBetween: {
		type: Boolean,
		default: false,
	},
	acSpaceAround: {
		type: Boolean,
		default: false,
	},
	acSpaceEvenly: {
		type: Boolean,
		default: false,
	},
	acStart: {
		type: Boolean,
		default: false,
	},
	acEnd: {
		type: Boolean,
		default: false,
	},
}

export const style = function () {

	let style = {}

	if (this.alignContent) {

		style.alignContent = this.alignContent
	}
	if (this.acCenter) {
		style.alignContent = 'center'
	}
	if (this.acStart) {
		style.alignContent = 'flex-start'
	}
	if (this.acEnd) {
		style.alignContent = 'flex-end'
	}
	if (this.acSpaceAround) {
		style.alignContent = 'space-around'
	}
	if (this.acSpaceBetween) {
		style.alignContent = 'space-between'
	}
	if (this.acSpaceEvenly) {
		style.alignContent = 'space-evenly'
	}

	return style
}

export default {
	props,
	style
}
