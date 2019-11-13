package com.abyte.wanandroid.contract;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.LoginEntity;

/**
 * Created by Jaygege on 2018/10/18
 */
public interface RegisterContract {

    interface View extends AbstractView {

        void onFailure(Throwable e);

        void onSuccess(LoginEntity loginEntity);
    }

    interface Presenter extends AbstractPresenter<View> {

        void register(String userName, String password, String rePassword);
    }
}
