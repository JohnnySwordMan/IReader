package com.abyte.wanandroid.base.response;

public class BaseResponse<T> {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    // 0-成功；1-失败
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
