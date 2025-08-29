/**
 * XSS过滤模块
 * @module util/xss
 */

import xss from 'xss';

/**
 * XSS过滤器
 */
export const xssFilter: xss.FilterXSS;

/**
 * 默认导出对象
 */
export default {
  whiteList: any;
  xssFilter: typeof xssFilter;
};