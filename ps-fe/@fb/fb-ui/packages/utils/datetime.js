/*!
 * datetime
 * (c) 2020 lincong1987
 */

import { isArray, isString, isNumber } from 'lodash-es'
import dayjs from 'dayjs'

// 对比时间，相等 0, 小于 -1 大于 1
export function timeFormat (time, format) {

	if (!time) {
		time = new Date()
	}

	if (isNumber(time)) {
		time = new Date(time)
	}

	if (isString(time)) {
		time = dayjs(time).toDate()
	}

	if (!time instanceof Date) {
		time = new Date()
	}

	const year = time.getFullYear()
	const month = time.getMonth()
	const day = time.getDate()
	const hours24 = time.getHours()
	const hours = hours24 % 12 === 0 ? 12 : hours24 % 12
	const minutes = time.getMinutes()
	const seconds = time.getSeconds()
	const milliseconds = time.getMilliseconds()
	const dd = t => (`0${t}`).slice(-2)
	const map = {
		YYYY: year,
		MM: dd(month + 1),
		MMMM: `${month + 1}月`,
		M: month + 1,
		DD: dd(day),
		D: day,
		HH: dd(hours24),
		H: hours24,
		hh: dd(hours),
		h: hours,
		mm: dd(minutes),
		m: minutes,
		ss: dd(seconds),
		s: seconds,
		S: milliseconds,
	}
	return (format).replace(
		/Y+|M+|D+|H+|h+|m+|s+|S+/g, str => map[str])
}

/**
 * 是否为同一天
 * @param date1
 * @param date2
 * @returns {boolean}
 */
export function isSameDay (date1, date2) {
	return timeFormat(date1, 'YYYY-MM-DD') === timeFormat(date2, 'YYYY-MM-DD')
}

/**
 * 比较日期
 * 大于返回1 等于返回0 小于返回-1
 * @param date1
 * @param date2
 * @param format
 * @returns {number}
 */
export function contrastDate (date1, date2, format = 'YYYY-MM-DD HH:mm:ss') {
	const t1 = timeFormat(date1, format)
	const t2 = timeFormat(date2, format)
	if (t1 > t2) return 1
	if (t1 === t2) return 0
	return -1
}

/**
 * 是否为空
 * @param val
 * @returns {boolean}
 */
export function isEmpty (val) {
	if (!val) return true
	if (isArray(val)) {
		return val.length === 0 || !val.some((item => item))
	}
	return false
}

/**
 *
 * @param val
 * @param dates
 * @returns {boolean|*}
 */
export function isEqual (mode, val, dates) {
	const emptyVal = isEmpty(val)
	const emptyDates = isEmpty(dates)
	if (emptyVal && emptyDates) return true
	if (emptyVal && !emptyDates || !emptyVal && emptyDates) return false
	if (mode === 'single') {
		return contrastDate(new Date(dayjs(dates).toDate()), new Date(dayjs(val).toDate())) === 0
	}
	if (val.length !== dates.length) return false
	return val.every((timestamp, i) => contrastDate(new Date(dayjs(dates[i]).toDate()),
		new Date(dayjs(timestamp).toDate())) === 0)
}

export function inOnePanel (dates, format) {
	const [start, end] = dates
	if (start && end) {
		if (format.indexOf('D') !== -1) {
			if (timeFormat(start, 'YYYYMM') === timeFormat(end, 'YYYYMM')) {
				return true
			}
		} else if (format.indexOf('M') !== -1) {
			if (start.getFullYear() === end.getFullYear()) {
				return true
			}
		} else if (Number.parseInt(start.getFullYear() / 10) ===
			Number.parseInt(end.getFullYear() / 10)) {
			return true
		}
	}
	return false
}

export default {timeFormat}
