package com.leyunone.codex.model;

/**
 * @author leyunone
 * @date 2021-08-13 15:07
 * <p>
 * 响应control的编码
 */
public enum ResponseCode {
    /**
     * 基本错误码
     */
    SUCCESS(200, "操作成功"),
    ERROR(404, "操作失败"),
    RPC_UNKNOWN_ERROR(100017, "远程服务调用未知错误"),
    RPC_TIMEOUT(100016, "远程服务调用超时"),
    RPC_ERROR_503(100015, "远程服务不可用"),

    /**
     * group
     */
    GROUP_NAME_REPEAT(1000001,"名字重复"),

    /**
     * user
     */
    USER_NAME_REPEAT(1001001,"名字重复"),

    /**
     * project
     */
    PROJECT_IS_EXIST(1002001,"项目已存在"),

    /**
     * bot
     */
    BOT_NOT_EXIST(1003001,"机器人任务不存在")

    ;

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
