package com.jaygege.smartx.base.exception;

/**
 * Created by geyan on 2018/9/21
 */
public class ApiException extends Exception {

    private int code;
    private String msg;

    public ApiException(){}

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
}
