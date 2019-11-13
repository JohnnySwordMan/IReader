package com.abyte.wanandroid.contract;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;

/**
 * 登录
 * Created by Jaygege on 2018/10/18
 */
public interface LoginContract {

    interface View extends AbstractView {

        void onLoginSuccess();

        void onLoginFailure(String errorMsg);
    }

    interface Presenter extends AbstractPresenter<View> {

        void login(String userName,String password);

    }
}
