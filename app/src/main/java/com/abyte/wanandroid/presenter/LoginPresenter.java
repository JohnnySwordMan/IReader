package com.abyte.wanandroid.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.abyte.wanandroid.base.DataManager;
import com.abyte.wanandroid.base.presenter.BasePresenter;
import com.abyte.wanandroid.contract.LoginContract;
import com.abyte.wanandroid.core.bean.LoginEntity;
import com.abyte.wanandroid.utils.RxJavaUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Jaygege on 2018/10/18
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private Context mContext;
    private final DataManager dataManager;

    public LoginPresenter(Context context) {
        this.mContext = context;
//        mGetLoginDataFromNet = new GetLoginDataFromNet();

        dataManager = DataManager.getInstance();
    }

    @Override
    public void login(String userName, String password) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            getView().showErrorMsg("用户名、密码不能为空");
            return;
        }

        Disposable subscriber = dataManager.getHttpService().getLoginData(userName, password)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        getView().onLoginSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onLoginFailure("用户名、密码错误");
                    }
                });
        addSubscriber(subscriber);

//        mGetLoginDataFromNet.setRequest(userName, password);
//        mGetLoginDataFromNet.execute(new Subscriber<LoginEntity>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//            @Override
//            public void onError(Throwable e) {
//                getView().onLoginFailure("用户名、密码错误");
//            }
//
//            @Override
//            public void onNext(LoginEntity loginEntity) {
//                Log.d("Login", "userName = " + loginEntity.username + ", password = " + loginEntity.password);
//                getView().onLoginSuccess();
//            }
//        });
    }
}
