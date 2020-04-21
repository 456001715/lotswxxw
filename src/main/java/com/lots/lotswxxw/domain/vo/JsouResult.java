package com.lots.lotswxxw.domain.vo;



/**
 * @author lifei
 * @date 2018/6/22
 */
public class JsouResult<T> {

    private static final int CODE_SUCCESS = 0;

    private static final String SUCCESS = "success";

    private Integer code = CODE_SUCCESS;

    private String msg = SUCCESS;

    private T data;

    private Long ts;

    public JsouResult() {
    }

    public JsouResult(T data) {
        this(CODE_SUCCESS, SUCCESS, data);
    }


    public JsouResult(Integer code, String msg) {
        this(code, msg, null);
    }

//    public JsouResult(String key, T value) {
//        this.ts = System.currentTimeMillis();
//        this.code = 0;
//        Map<String, T> m = new HashMap<String, T>(1);
//        m.put(key, value);
//        data = m;
//    }

    public JsouResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        ts = System.currentTimeMillis();
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

    public void setData(final Integer code, final String msg,final T data) {
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
