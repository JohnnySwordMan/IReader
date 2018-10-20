package com.jaygege.smartx.core.httpUseCase;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import rx.Observable;

/**
 * 登录接口
 * Created by Jaygege on 2018/10/18
 */
public class GetLoginDataFromNet extends SimpleNetRxHttp {

    private String userName;
    private String password;

    public GetLoginDataFromNet() {

    }

    public void setRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * 通过map操作符，去掉BaseResponse
     */
    @Override
    protected Observable<LoginEntity> create() {
        Observable<LoginEntity> loginEntity = RetrofitUtils.getHttpService().getLoginData(userName, password).map(new BaseResponseFunc<>());
        return loginEntity;
    }
}
