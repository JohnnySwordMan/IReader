package com.jaygege.smartx.core.httpUseCase;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import rx.Observable;

/**
 * Created by Jaygege on 2018/10/18
 */
public class GetRegisterDataFromNet extends SimpleNetRxHttp {

    private String userName;
    private String password;
    private String rePassword;

    public void setRequest(String userName, String password, String rePassword) {
        this.userName = userName;
        this.password = password;
        this.rePassword = rePassword;
    }

    @Override
    protected Observable<LoginEntity> create() {

        Observable<LoginEntity> registerEntity = RetrofitUtils.getHttpService().getRegisterData(userName, password, rePassword).map(new BaseResponseFunc<>());
        return registerEntity;
    }
}
