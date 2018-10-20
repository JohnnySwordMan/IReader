package com.jaygege.smartx.base.response;

import com.jaygege.smartx.base.ApiException;

import rx.functions.Func1;

/**
 * Created by geyan on 2018/9/22
 */
public class BaseResponseFunc<T> implements Func1<BaseResponse<T>, T> {
    @Override
    public T call(BaseResponse<T> response) {
        if (response.getErrorCode() != 0) {
            throw new ApiException(response.getErrorMsg());
        }
        return response.getData();
    }
}
