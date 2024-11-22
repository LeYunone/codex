package com.leyunone.codex.system.exception;

import com.leyunone.codex.model.ResponseCode;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/14 16:38
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 结果code
     */
    private ResponseCode resultCode = ResponseCode.SUCCESS;

    public ServiceException(ResponseCode resultCode) {
        // 使用父类的 message 字段
        super(resultCode.getMessage());
        // 设置错误码
        this.resultCode = resultCode;
    }

    public ServiceException(ResponseCode resultCode, Throwable cause) {
        // 使用父类的 message 字段
        super(resultCode.getMessage(), cause);
        // 设置错误码
        this.resultCode = resultCode;
    }

    public ServiceException(ResponseCode resultCode, String message) {
        // 使用父类的 message 字段
        super(message);
        // 设置错误码
        this.resultCode = resultCode;
    }

    public ServiceException(ResponseCode resultCode, String message, Throwable cause) {
        // 使用父类的 message 字段
        super(message, cause);
        // 设置错误码
        this.resultCode = resultCode;
    }

    public ResponseCode getResultCode() {
        return resultCode;
    }

    public int getCode() {
        return this.resultCode.getCode();
    }
}