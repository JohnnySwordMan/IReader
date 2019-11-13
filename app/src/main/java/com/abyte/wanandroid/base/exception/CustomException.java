package com.abyte.wanandroid.base.exception;

import com.google.gson.JsonParseException;

import java.net.ConnectException;

/**
 * 本地产生的异常
 */
public class CustomException {

    public static final int UNKNOWN = 1000;

    public static final int PARSE_ERROR = 1001;

    public static final int NETWORK_ERROR = 1002;
    private static ApiException exception;

    public static ApiException handleException(Throwable e) {
        if (e instanceof JsonParseException) {
            exception = new ApiException(PARSE_ERROR, e.getMessage());
            return exception;
        } else if (e instanceof ConnectException) {
            exception = new ApiException(NETWORK_ERROR, e.getMessage());
            return exception;
        } else {
            exception = new ApiException(UNKNOWN, e.getMessage());
            return exception;
        }
    }
}
