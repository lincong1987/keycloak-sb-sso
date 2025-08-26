/**
 * index.js
 */
import axios from 'axios';
import storage from '../storage';
import { isArray, isNull, isPlainObject, isString } from 'lodash-es'

const trim = function (obj) {
    Object.keys(obj).forEach((p) => {
        if (isString(obj[p])) {
            obj[p] = obj[p].trim();
        } else if (isPlainObject(obj[p])) {
            trim(obj[p]);
        } else if (isArray(obj[p])) {
            trim(obj[p]);
        }
    });
};

const requsetLog = {
    data: storage.get('FB_AJAX_LOG') || [],
    importantApi: {},
    creatLog(url, data, status) {
        let _data;
        if (data) {
            _data = JSON.stringify(data);
        }
        if (_data && _data.length > 1000) {
            data = _data.slice(0, 1000); // 大约1K
        }
        const now = new Date().getTime();
        const obj = {
            url,
            data,
            timestamp: now,
            status: status || 'send'
        };
        if (this.data.length >= 500) {
            this.data.shift();
        }
        this.data.push(obj);
        try {
            storage.set('FB_AJAX_LOG', this.data);
        } catch (e) {
            storage.remove('FB_AJAX_LOG');
            this.data = [obj];
            storage.set('FB_AJAX_LOG', this.data);
        }
        return obj;
    },
    changeLogStatus(log, newStatus) {
        const logs = this.data.filter(obj => obj.timestamp === log.timestamp);
        if (logs.length > 0) {
            logs[0].status = newStatus;
            storage.set('FB_AJAX_LOG', this.data);
        }
    },
    getLogByURL(url, data) {
        return this.data.filter(obj => obj.url === url && JSON.stringify(data) === JSON.stringify(obj.data));
    }
};

const instance = axios.create({
    method: 'post',
    baseURL: "/",
    timeout: 10000,
    withCredentials: true
});

// "code": -1,
//     "message": "",
//     "data": {},
// "expand": ""


const api = {
    instance,
    error: {},
    responseReader: {
        code: 'code',
        success: '0',
        message: 'message',
        data: 'data'
    }
};

const getData = function (data, resultFormat) {
    const _arr = ['codePath', 'messagePath', 'resultPath'];
    const arr = []; const
        rst = {};
    for (let i = 0; i < _arr.length; i++) {
        const pathArray = resultFormat[_arr[i]].split('.');
        const pathLength = pathArray.length;
        let result;
        if (pathLength === 1 && pathArray[0] === '*') {
            result = data;
        } else {
            result = data[pathArray[0]];
        }
        for (let j = 1; j < pathLength; j++) {
            result = result[pathArray[j]];
            if (!result) {
                if (j < pathLength - 1) {
                    console.error(`【FB】api responseReader 配置错误：${_arr[i]}拿到的值是undefined，请检查配置`);
                }
                break;
            }
        }
        arr.push(result);
    }
    rst.code = arr[0];
    rst.message = arr[1];
    rst.result = arr[2];
    return rst;
};

const success = function (response) {
    // 响应结构
    const resultFormat = (response.config && response.config.resultFormat) || api.responseReader;
    // 哪些code不处理错误
    const ignoreCode = (response.config && response.config.ignoreCode) || [];
    if (isNull(resultFormat.codePath) || isNull(resultFormat.successCode)
        || isNull(resultFormat.messagePath) || isNull(resultFormat.resultPath)) {
        console.error('【FB】Api配置错误: 请调用setResponseReader来设置API的响应结构');
        return null;
    }

    let data;
    if (isString(response.data)) {
        data = JSON.parse(response.data);
    } else if (isObject(response.data)) {
        data = response.data;
    } else {
        throw new Error("");
    }

    const { code, message, result } = getData(data, resultFormat);

    if (code !== resultFormat.successCode) {
        let _message = '';
        if (api.error[code]) {
            api.error[code].forEach(fn => fn(response));
        } else if (!ignoreCode.includes(code) && ignoreCode !== '*') {
            _message = message || "接口异常，请联系管理员。";
        }
        const error = new Error(_message);
        error.response = response;
        throw error;
    }
    return result || {};
};

const fail = function (error) {
    let _message = '';
    const response = error.response;
    if (response && api.error[response.status]) {
        api.error[response.status].forEach(fn => fn(response));
    } else {
        _message =  "接口异常，请联系管理员。";
        try {
            if (response && response.data) {
                let data;
                if (isString(response.data)) {
                    data = JSON.parse(response.data);
                } else if (isObject(response.data)) {
                    data = response.data;
                }
                if (data) {
                    const { message } = getData(data, (response.config && response.config.resultFormat) || api.responseReader);
                    _message = message;
                }
            }
        } catch (e) {
            // 可以啥都不做
        }
    }
    error.message = _message;
    throw error;
};

const param = function (url, data, option) {
    const method = instance.defaults.method || 'post';
    if (isNull(url)) {
        return console.error('请传入URL');
    } if (!isNull(url) && isNull(data) && isNull(option)) {
        option = {
            method
        };
    } else if (!isNull(url) && !isNull(data) && isNull(option)) {
        option = {
            method
        };
        if (isString(data)) {
            option.method = data;
        } else if (isObject(data)) {
            option.data = data;
        }
    } else if (!isNull(url) && !isNull(data) && !isNull(option)) {
        if (!isObject(data)) {
            data = {};
        }
        if (isString(option)) {
            option = {
                method: option
            };
        } else if (isObject(option)) {
            option.method = option.method || method;
        } else {
            option = {
                method
            };
        }
        if (option.method === 'get' || option.method === 'delete' || option.method === 'head' || option.method === 'options') {
            option.params = data;
        }
        if (option.method === 'post' || option.method === 'put' || option.method === 'patch') {
            option.data = data;
        }
    }
    // 过滤参数中的空格
    const _data = option.params || option.data;
    if (_data && isObject(_data) && option.trim !== false) {
        trim(_data);
    }

    option.url = url;

    // 如果传了button
    if (option.button) {
        option.button.currentDisabled = true;
    }

    return instance.request(option);
};

const action = function (url, data, option) {
    // 记录日志
    const log = requsetLog.creatLog(url, data);

    return param(url, data, option)
        .then(success, fail)
        .then((response) => {
            requsetLog.changeLogStatus(log, 'success');
            if (option && option.button) {
                option.button.currentDisabled = false;
            }
            return response;
        })
        .catch((error) => {
            requsetLog.changeLogStatus(log, 'fail');
            if (option && option.button) {
                option.button.currentDisabled = false;
            }
            error.message && window.Toast.error(error.message);
            throw error;
        });
};

api.fetch = function (url, data, option) {
    // if (requsetLog.importantApi[url]) {
    //     const logs = requsetLog.getLogByURL(url, data);
    //     if (logs.length > 0) {
    //         const compareLog = logs[logs.length - 1];
    //         if (compareLog.status === 'compare') {
    //             requsetLog.creatLog(url, data, 'notAllowed');
    //             return {
    //                 then: () => {}
    //             };
    //         }
    //         const importantApiOption = requsetLog.importantApi[url];
    //         const control = importantApiOption.control || 10000;
    //         const message = importantApiOption.message || format('fesMessages.importInterfaceTip', { s: control / 1000 });
    //         if (new Date().getTime() - compareLog.timestamp < control) {
    //             const oldStatus = compareLog.status;
    //             requsetLog.changeLogStatus(compareLog, 'compare');
    //             return new Promise(((resolve, reject) => {
    //                 window.Message.confirm(format('fesMessages.tip'), message).then((index) => {
    //                     if (compareLog.status === 'compare') {
    //                         requsetLog.changeLogStatus(compareLog, oldStatus);
    //                     }
    //                     if (index === 0) {
    //                         resolve(action(url, data, option));
    //                     } else {
    //                         reject(new Error('不允许相同操作间隔过小'));
    //                     }
    //                 });
    //             }));
    //         }
    //         return action(url, data, option);
    //     }
    //     return action(url, data, option);
    // }
    return action(url, data, option);
};

/**
 * 设置 request Header
 * @param headers Object
 */
api.setHeader = function (headers = {}) {
    Object.keys(headers).forEach((p) => {
        if (['delete', 'get', 'head', 'post', 'put', 'patch', 'common'].includes(p)) {
            instance.defaults.headers[p] = Object.assign({}, instance.defaults.headers[p], headers[p]);
        } else {
            instance.defaults.headers.common[p] = headers[p];
        }
    });
};

/**
 * 配置ajax请求参数
 * @param option
 */
api.option = function (option = {}) {
    const {
        root,
        baseURL,
        timeout,
        headers,
        config,
        ...others
    } = option;
    if (root || baseURL) {
        instance.defaults.baseURL = root || baseURL;
    }
    if (timeout && isNumber(timeout)) {
        instance.defaults.timeout = timeout;
    }
    if (headers) {
        api.setHeader(headers);
    }
    const otherPropertys = Object.assign({}, others, config);
    Object.keys(otherPropertys).forEach((p) => {
        instance.defaults[p] = otherPropertys[p];
    });
};

/**
 * 请求拦截器
 * @param before function 请求之前的拦截器
 */
api.setReqInterceptor = function (before, error) {
    if (Array.isArray(before)) {
        return instance.interceptors.request.use(...before);
    }
    return instance.interceptors.request.use(before, error);
};
api.ejectReqInterceptor = function (interceptor) {
    return instance.interceptors.request.eject(interceptor);
};

/**
 * 响应拦截器
 * @param after function 响应之后的拦截器
 */
api.setResInterceptor = function (after, error) {
    if (Array.isArray(after)) {
        return instance.interceptors.response.use(...after);
    }
    return instance.interceptors.response.use(after, error);
};
api.ejectResInterceptor = function (interceptor) {
    return instance.interceptors.response.eject(interceptor);
};

/**
 * 配置错误响应
 * @param option
 */
api.setError = function (option) {
    if (option && isObject(option)) {
        Object.keys(option).forEach((key) => {
            if (!isArray(api.error[key])) {
                api.error[key] = [];
            }
            api.error[key].push(option[key]);
        });
    }
};

/**
 * 设置响应结构
 * @param responseReader
 */
api.setResponseReader = function (responseReader) {
    this.responseReader = responseReader;
};

/**
 *  配置重要请求
 */
api.setImportant = function (option) {
    if (option && isObject(option)) {
        requsetLog.importantApi = option;
    } else {
        console.error('【FB】ImportantApi配置错误: 参数必须是对象{"/get": { message:"xxx", control: 10000 } }');
    }
};

export default api;
