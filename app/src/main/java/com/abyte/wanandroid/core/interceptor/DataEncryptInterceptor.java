package com.abyte.wanandroid.core.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求的加解密拦截器
 */
public class DataEncryptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        // 拿到请求参数
        Request request = chain.request();
        RequestBody oldRequestBody = request.body();

        return null;
    }
}
