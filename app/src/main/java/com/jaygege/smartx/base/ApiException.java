package com.jaygege.smartx.base;

/**
 * Created by geyan on 2018/9/21
 */
public class ApiException extends RuntimeException {

    public ApiException(int code, String msg) {
        this(getApiExceptionMsg(code, msg));
    }

    public ApiException(String msg) {
        super(msg);
    }

    private static String getApiExceptionMsg(int code, String msg) {
        return msg;
    }
}
