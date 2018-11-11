package com.jaygege.smartx.base;

import com.jaygege.smartx.network.HttpService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 数据管理
 */
public class DataManager {

    private Retrofit retrofit;
    private static volatile HttpService httpService;

    private DataManager() {
    }

    public static DataManager getInstance() {
        return DataManagerHolder.mInstance;
    }

    private static class DataManagerHolder {
        private static final DataManager mInstance = new DataManager();
    }

    // 这边不需要单例，因为init只会在application中执行
    public void init() {
        // 这里可以添加拦截器，后续我会添加加密解密的拦截器
        // 加密解密拦截器，两种实现方式都写一下
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public HttpService getHttpService() {
        if (httpService == null) {
            synchronized (DataManager.class) {
                if (httpService == null) {
                    httpService = retrofit.create(HttpService.class);
                }
            }
        }
        return httpService;
    }
}
