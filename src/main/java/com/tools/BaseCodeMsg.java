package com.tools;

public class BaseCodeMsg {

    private String msg;
    private String data;
    private int code;

    public BaseCodeMsg() {
    }

    public BaseCodeMsg(String msg,  int code) {
        this.msg = msg;
        this.code = code;
    }

    public static BaseCodeMsg app(String msg, int code) {
       return new BaseCodeMsg(msg,code);
    };

    public BaseCodeMsg fillArgs(Object ... args) {
        this.msg = String.format(this.msg, args);
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    /* 成功 */
    public static final BaseCodeMsg SUCCESS = BaseCodeMsg.app("success", 200);

    /* 失败 */
    public static final BaseCodeMsg ERROR_INIT = BaseCodeMsg.app("error", -1);


}
