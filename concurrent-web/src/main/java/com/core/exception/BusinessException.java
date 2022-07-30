package com.core.exception;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义业务异常,需要传递给前端展示错误信息
 * @author
 */
@Getter
@Slf4j
public class BusinessException extends RuntimeException {
    private Integer status;
    private ErrInfo errInfo;
    private String message;
    /**
     * 传递给前端的对象
     */
    private Object result;

    public BusinessException(String message) {
        this.message = message;
        this.status = ExceptionEnum.CUSTOM_ERROR.getCode();
        this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
    }

    public BusinessException(Integer status, String message) {
        this.message = message;
        this.status = status;
        this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
    }

    public BusinessException(StatusCodeEnum statusCodeEnum, String message, Object result) {
        this.message = message;
        this.status = statusCodeEnum.getCode();
        this.result = result;
        this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
    }

    public BusinessException(Integer status, String message, Object... objects) {
        this.message = message;
        this.status = status;
        this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
        printParams(objects);
    }

    public BusinessException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    public BusinessException(ExceptionEnum exceptionEnum) {
        this.message = exceptionEnum.getMessage();
        this.status = exceptionEnum.getCode();
        this.errInfo = new ErrInfo(System.currentTimeMillis(), exceptionEnum.getMessage());
    }

    public BusinessException(ExceptionEnum exceptionEnum, Object... objects) {
        this.message = exceptionEnum.getMessage();
        this.status = exceptionEnum.getCode();
        this.errInfo = new ErrInfo(System.currentTimeMillis(), exceptionEnum.getMessage());
        printParams(objects);
    }

    public BusinessException(StatusCodeEnum statusCodeEnum, String message) {
        this.message = message;
        this.status = statusCodeEnum.getCode();
        this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
    }

    private void printParams(Object[] params) {
        log.error("time: {}, params: {} ",System.currentTimeMillis(), JSON.toJSONString(params));
    }

    public void setMessage(String message) {
        this.message = message;
        if (this.errInfo != null) {
            this.errInfo.setMessage(message);
        }else{
            this.errInfo = new ErrInfo(System.currentTimeMillis(), message);
        }
    }
}
