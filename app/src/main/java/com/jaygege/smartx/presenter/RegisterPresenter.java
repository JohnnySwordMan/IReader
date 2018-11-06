package com.jaygege.smartx.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.RegisterContract;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.core.httpUseCase.GetRegisterDataFromNet;

import rx.Subscriber;

/**
 * Created by Jaygege on 2018/10/18
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private final GetRegisterDataFromNet mGetRegisterDataFromNet;
    private Context mContext;

    public RegisterPresenter(Context context) {
        this.mContext = context;
        mGetRegisterDataFromNet = new GetRegisterDataFromNet();
    }

    @Override
    public void register(String userName, String password, String rePassword) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            getView().showErrorMsg("用户名、密码不能为空");
            return;
        }
        if (!password.equals(rePassword)) {
            getView().showErrorMsg("两次密码不一致");
            return;
        }
        mGetRegisterDataFromNet.setRequest(userName, password, rePassword);
        mGetRegisterDataFromNet.execute(new Subscriber<LoginEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("register", "onError");
                getView().onFailure(e);
            }

            @Override
            public void onNext(LoginEntity loginEntity) {
                Log.d("register", "注册成功 = " + loginEntity.toString());
                if (loginEntity != null) {
                    getView().onSuccess(loginEntity);
                } else {
                    getView().showErrorMsg("注册失败");
                }
            }
        });
    }
}
