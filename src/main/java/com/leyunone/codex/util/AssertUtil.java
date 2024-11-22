package com.leyunone.codex.util;

import com.leyunone.codex.model.ResponseCode;
import com.leyunone.codex.system.exception.ServiceException;

/**
 * @author LeYunone
 * @create 2022/6/21
 * 断言报错
 */
public class AssertUtil {

    /**
     * 自定义响应体
     */
    static class CustomResultCode {

        private int code;

        private String message;

        private CustomResultCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private CustomResultCode(String message) {
            //500 服务器错误
            this.code = 500;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * 自定义message
     */
    static class CustomResultMessage {
        private static final String SYSTEM_ERROR = "系统未知异常";
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, CustomResultMessage.SYSTEM_ERROR);
    }

    public static void isTrue(boolean expression, String message) {
        CustomResultCode customResultCode = new CustomResultCode(message);
        isFalse(!expression, customResultCode);
    }

    public static void isTrue(boolean expression, ResponseCode code) {
        isFalse(!expression, code);
    }

    public static void isTrue(boolean expression, String message, int code) {
        CustomResultCode customResultCode = new CustomResultCode(code, message);
        isFalse(!expression, customResultCode);
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, CustomResultMessage.SYSTEM_ERROR);
    }

    public static void isFalse(boolean expression, String message) {
        CustomResultCode customResultCode = new CustomResultCode(message);
        isFalse(expression, customResultCode);
    }

    public static void isFalse(boolean expression, String message, int code) {
        CustomResultCode customResultCode = new CustomResultCode(code, message);
        isFalse(expression, customResultCode);
    }

    public static void isFalse(boolean expression, CustomResultCode code) {
        if (expression) {
            throw new ServiceException(code.getMessage());
        }
    }

    public static void isFalse(boolean expression, ResponseCode code) {
        if (expression) {
            throw new ServiceException(code);
        }
    }

}
