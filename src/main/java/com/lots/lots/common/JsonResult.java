package com.lots.lots.common;


/**
 * @author: lots
 * @date: 2020/4/22 11:11
 * @description:
 */
public class JsonResult<T> {

    private static final int CODE_SUCCESS = 200;

    private static final String SUCCESS = "success";

    private Integer code = CODE_SUCCESS;

    private String msg = SUCCESS;

    private T data;

    private Long ts;

    public JsonResult() {
    }

    public JsonResult(Throwable e) {
        code = ResultCode.FAILED.getCode();
        data = null;
        msg = e.getMessage();
    }

    public JsonResult(T data) {
        this(CODE_SUCCESS, SUCCESS, data);
    }


    public JsonResult(Integer code) {
        this(code, null, null);
    }

    public JsonResult(Integer code, String msg) {
        this(code, msg, null);
    }


    public JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        ts = System.currentTimeMillis();
    }

    /**
     * 未登录返回结果
     */
    public static <T> JsonResult<T> unauthorized(T data) {
        return new JsonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> JsonResult<T> forbidden(T data) {
        return new JsonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> JsonResult<T> success() {
        return new JsonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> JsonResult<T> success(String message, T data) {
        return new JsonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> JsonResult<T> failed(String message) {
        return new JsonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }


    /**
     * 失败返回结果
     */
    public static <T> JsonResult<T> failed() {
        return new JsonResult<T>(ResultCode.FAILED.getCode(), null, null);
    }

    /**
     * 参数验证失败返回结果
     * @return
     */
    public static <T> JsonResult validateFailed() {
        return failed();
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> JsonResult<T> validateFailed(String message) {
        return new JsonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public Long getTs() {
        return this.ts;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setData(final Integer code, final String msg, final T data) {
        this.data = data;
    }

    public void setTs(final Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", msg:'" + msg + '\'' +
                ", data:" + data +
                ", ts:" + ts +
                '}';
    }
}
