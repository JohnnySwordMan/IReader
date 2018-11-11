package com.jaygege.smartx.utils;

import com.jaygege.smartx.base.exception.ApiException;
import com.jaygege.smartx.base.exception.CustomException;
import com.jaygege.smartx.base.response.BaseResponse;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaUtils {

    /**
     * 将BaseResponse<T> 转换成 T
     * 先检查是否是本地异常，例如解析错误，网络错误等
     * onErrorResumeNet：如果发送的Observable遇到错误，会新建Observable替换源Observable
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream -> upstream.onErrorResumeNext(throwable -> {
            return Observable.error(CustomException.handleException(throwable));
        }).flatMap(new ResponseFunction<>());
    }


    /**
     * 线程切换
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> response) throws Exception {
            int code = response.getCode();
            String errorMsg = response.getMessage();
            if (code == BaseResponse.SUCCESS) {
                return createData(response.getData());
            } else {
                return Observable.error(new ApiException(code, errorMsg));
            }
        }

        private static <T> Observable<T> createData(T t) {
            return Observable.create(emitter -> {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
        }
    }
}
