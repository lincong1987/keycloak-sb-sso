/**
 * 服务模块
 * @module service/index
 */

import axios, {
  AxiosAdapter,
  AxiosBasicCredentials,
  AxiosProxyConfig,
  AxiosTransformer,
  CancelToken,
  Method,
  ResponseType,
} from 'axios';

/**
 * 服务对象
 */
export const services: any;

/**
 * 创建服务实例
 * @param config - 配置对象
 * @param app - 应用实例
 * @returns Axios实例
 */
export function createService(config: any, app: any): any;

/**
 * 安装服务
 * @param app - 应用实例
 * @param serviceConfig - 服务配置数组
 * @returns 服务对象
 */
export function installService(app: any, serviceConfig?: Array<any>): any;

/**
 * 默认导出对象
 */
export default {
  axios: any;
  installService: (app: any, serviceConfig?: Array<any>) => any;
  createService: (config: any, app: any) => any;
};