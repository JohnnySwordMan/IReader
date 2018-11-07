//package com.jaygege.smartx.network;
//
//import com.jaygege.smartx.base.Constant;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by geyan on 2018/9/21
// */
//public class RetrofitUtils {
//
//    private volatile static Retrofit retrofit;
//    private volatile static HttpService mHttpService;
//    private static final String baseUrl = Constant.BASE_URL;
//
//    private static Retrofit getInstance() {
//        if (retrofit == null) {
//            synchronized (RetrofitUtils.class) {
//                if (retrofit == null) {
//                    retrofit = new Retrofit.Builder()
//                            .baseUrl(baseUrl)
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  // 网络请求适配器，支持RxJava
//                            .addConverterFactory(GsonConverterFactory.create())   // 设置数据解析器
//                            .build();
//                }
//            }
//        }
//        return retrofit;
//    }
//
//    public static HttpService getHttpService() {
//        if (mHttpService == null) {
//            synchronized (RetrofitUtils.class) {
//                if (mHttpService == null) {
//                    mHttpService = getInstance().create(HttpService.class);
//                }
//            }
//        }
//        return mHttpService;
//    }
//}
