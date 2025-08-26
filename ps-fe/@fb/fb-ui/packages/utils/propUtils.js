/*!
 * propUtils
 * (c) 2020 lincong1987
 */

import {
	endsWith,
	includes, isNumber,
	isString,
	isUndefined,
	keysIn,
	parseInt,
} from 'lodash-es'

const nestRE = /^(attrs|props|on|nativeOn|class|style|hook)$/

export function mergeJSXProps (objs) {
	return objs.reduce(function (a, b) {
		var aa, bb, key, nestedKey, temp
		for (key in b) {
			aa = a[key]
			bb = b[key]
			if (aa && nestRE.test(key)) {
				// normalize class
				if (key === 'class') {
					if (typeof aa === 'string') {
						temp = aa
						a[key] = aa = {}
						aa[temp] = true
					}
					if (typeof bb === 'string') {
						temp = bb
						b[key] = bb = {}
						bb[temp] = true
					}
				}
				if (key === 'on' || key === 'nativeOn' || key === 'hook') {
					// merge functions
					for (nestedKey in bb) {
						aa[nestedKey] = mergeFn(aa[nestedKey], bb[nestedKey])
					}
				} else if (Array.isArray(aa)) {
					a[key] = aa.concat(bb)
				} else if (Array.isArray(bb)) {
					a[key] = [aa].concat(bb)
				} else {
					for (nestedKey in bb) {
						aa[nestedKey] = bb[nestedKey]
					}
				}
			} else {
				a[key] = b[key]
			}
		}
		return a
	}, {})
}

export function mergeFn (a, b) {
	return function () {
		a && a.apply(this, arguments)
		b && b.apply(this, arguments)
	}
}

/**
 * 智能获取尺寸
 * @param {String|Number} size 可以是 0 10 10px 10% x xl
 * 如果是 x xl 这样的要传入 sizeMap = {x: 30, xl: '100px'}
 * 在组件的props里最好这样定义
 * props :{
 *     width: {
				type: [String, Number],
				default: 256,
			}
 * }
 * @param {Object} sizeMap 尺寸码表 {x: 30, xl: '100px'}
 * @returns {number|string|undefined|*}
 */
export function getSizeStyle (size, sizeMap = {}) {

	if (isUndefined(size)) {
		return 0
	}

	if (isString(size)) {
		if (endsWith(size, 'px')) {
			return size
		}

		if (endsWith(size, '%')) {
			return size
		}

		if (includes(keysIn(sizeMap), size)) {
			return getSizeStyle(sizeMap[size])
		}
	}

	let _size = parseInt(size, 10)

	if (_size.toFixed() == 'NaN') {
		return 0
	}

	if (isNumber(_size)) {
		return `${size}px`
	}

}

export default {
	mergeJSXProps,
	mergeFn,
}
