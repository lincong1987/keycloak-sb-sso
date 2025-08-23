package com.jiuxi.mvc.exception;

import com.jiuxi.common.bean.ErrorCode;
import com.jiuxi.common.bean.JsonResponse;
import com.jiuxi.common.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;




/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理
 * @Author: 杨攀
 * @Date: 2020/3/10 11:24
 * @Copyright: 2020 www.tuxun.net Inc. All rights reserved.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    protected static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * @param e
     * @return
     * @Description: 处理未定义的其他异常信息
     * @Author:杨攀
     * @Since: 2020/3/10日下午3:28:14
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResponse defultExcepitonHandler(Exception e) {

        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        //e.printStackTrace();

        // 返回前端错误信息
        return JsonResponse.build(ErrorCode.ERROR.getCode(), "处理异常，请联系管理员！");
    }

    /**
     * @param e
     * @return
     * @Description: 处理未定义的其他异常信息
     * @Author:杨攀
     * @Since: 2020/3/10日下午3:28:14
     */
    @ExceptionHandler(value = RuntimeException.class)
    public JsonResponse defultRuntimeExcepitonHandler(Exception e) {

        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        //e.printStackTrace();

        // 返回前端错误信息
        return JsonResponse.build(ErrorCode.ERROR.getCode(), e.getMessage());
    }




    /**
     * @param e
     * @return
     * @Description: 自定义异常
     * @Author:杨攀
     * @Since: 2020/3/10 日下午4:47:46
     */
    @ExceptionHandler(value = {com.jiuxi.core.bean.TopinfoRuntimeException.class})
    public JsonResponse topinfoRuntimeException(com.jiuxi.core.bean.TopinfoRuntimeException e) {

        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        // 返回前端错误信息
        return JsonResponse.build(e.getErrcode(), e.getMessage());
    }


    /**
     * 文件大小超过了允许的最大限制
     *
     * @param e
     * @return com.jiuxi.common.bean.JsonResponse
     * @author 杨攀
     * @date 2022/12/8 16:45
     */
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public JsonResponse maxUploadSizeExceededException(MaxUploadSizeExceededException e) {

        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        // 返回前端错误信息
        return JsonResponse.build(ErrorCode.ERROR.getCode(), "上传失败，文件大小超过了允许的最大限制！");
    }

    /**
     * sql 异常
     *
     * @return
     * @author 杨攀
     * @date 2021/7/7 9:27
     */
    @ExceptionHandler(value = {SQLException.class})
    public JsonResponse sqlException(SQLException e) {
        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        // 返回前端错误信息
        return JsonResponse.build(ErrorCode.ERROR.getCode(), "系统异常，请联系管理员! SQLException");
    }

    /**
     * sql 异常
     *
     * @return
     * @author 杨攀
     * @date 2021/7/7 9:27
     */
    @ExceptionHandler(value = {DataAccessException.class})
    public JsonResponse dataAccessException(DataAccessException e) {
        // 打印错误日志
        logger.error("ErrorCode:{}, Message:{}", ErrorCode.ERROR.getCode(), ExceptionUtils.getStackTrace(e));
        // 返回前端错误信息
        return JsonResponse.build(ErrorCode.ERROR.getCode(), "系统异常，请联系管理员! DataAccessException");
    }

    /**
     * @param e
     * @return com.jiuxi.mvc.bean.JsonResponse
     * @description: 校验异常
     * @author 杨攀
     * @date 2020/7/21 8:56
     */
    @ExceptionHandler(value = {ValidationException.class, ConstraintViolationException.class})
    public JsonResponse validationExceptionHandler(Exception e) {
        logger.error("参数校验异常", e);
        String message = "参数校验异常";
        if (e.getMessage() != null) {
            message = e.getMessage();
        }
        return JsonResponse.build(ErrorCode.ERROR.getCode(), message);
    }


}
