package com.jaygege.smartx.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.LoginContract;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.core.httpUseCase.GetLoginDataFromNet;

import rx.Subscriber;

/**
 * Created by Jaygege on 2018/10/18
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private final GetLoginDataFromNet mGetLoginDataFromNet;
    private Context mContext;

    public LoginPresenter(Context context) {
        this.mContext = context;
        mGetLoginDataFromNet = new GetLoginDataFromNet();
    }

    @Override
    public void login(String userName, String password) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            getView().showErrorMsg("用户名、密码不能为空");
            return;
        }
        mGetLoginDataFromNet.setRequest(userName, password);
        mGetLoginDataFromNet.execute(new Subscriber<LoginEntity>() {

            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                getView().onLoginFailure("用户名、密码错误");
            }

            @Override
            public void onNext(LoginEntity loginEntity) {
                Log.d("Login", "userName = " + loginEntity.username + ", password = " + loginEntity.password);
                getView().onLoginSuccess();
            }
        });
    }
}
