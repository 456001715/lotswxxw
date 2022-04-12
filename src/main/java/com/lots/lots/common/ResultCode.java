package com.lots.lots.common;

/**
 * 枚举了一些常用API操作码
 *
 * @author lots
 */
public enum ResultCode {

    /**
     * 操作成功
     *
     * @author: lots
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     *
     * @author: lots
     */
    FAILED(500, "操作失败"),
    /**
     * 参数检验失败
     *
     * @author: lots
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    /**
     * 暂未登录或token已经过期
     *
     * @author: lots
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     *
     * @author: lots
     */
    FORBIDDEN(403, "没有相关权限");
    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
