package com.jaygege.smartx.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jaygege.smartx.base.DataManager;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.RegisterContract;
import com.jaygege.smartx.core.bean.LoginEntity;
import com.jaygege.smartx.utils.RxJavaUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Jaygege on 2018/10/18
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private Context mContext;
    private final DataManager dataManager;

    public RegisterPresenter(Context context) {
        this.mContext = context;
//        mGetRegisterDataFromNet = new GetRegisterDataFromNet();
        dataManager = DataManager.getInstance();
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

        Disposable subcriber = dataManager.getHttpService().getRegisterData(userName, password, rePassword)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<LoginEntity>() {
                    @Override
                    public void accept(LoginEntity loginEntity) throws Exception {
                        if (loginEntity != null) {
                            getView().onSuccess(loginEntity);
                        } else {
                            getView().showErrorMsg("注册失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onFailure(throwable);
                    }
                });

        addSubscriber(subcriber);

//        mGetRegisterDataFromNet.setRequest(userName, password, rePassword);
//        mGetRegisterDataFromNet.execute(new Subscriber<LoginEntity>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("register", "onError");
//                getView().onFailure(e);
//            }
//
//            @Override
//            public void onNext(LoginEntity loginEntity) {
//                Log.d("register", "注册成功 = " + loginEntity.toString());
//                if (loginEntity != null) {
//                    getView().onSuccess(loginEntity);
//                } else {
//                    getView().showErrorMsg("注册失败");
//                }
//            }
//        });
    }
}
