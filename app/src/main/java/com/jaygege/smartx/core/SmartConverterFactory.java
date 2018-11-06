package com.jaygege.smartx.core;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义ConverterFactory类，在网络请求的时候进行加解密
 * Created by geyan on 2018
 */
public class SmartConverterFactory extends Converter.Factory {

    private Gson gson;

    // 默认
    public static SmartConverterFactory create() {
        return create(new Gson());
    }

    public static SmartConverterFactory create(Gson gson) {
        return new SmartConverterFactory(gson);
    }

    private SmartConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson is null");
        }
        this.gson = gson;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        // TypeAdapter用来进行序列化和反序列化的
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        // 请求
        return new SmartRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    public static class SmartRequestBodyConverter<T> implements Converter<T, RequestBody> {

        private Gson gson;
        private TypeAdapter<T> adapter;

        public SmartRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            /**
             * 1. request是将T类型数据，转换成json
             * 2. 转换之后，加密，然后传给服务端
             */
            return null;
        }
    }
}
