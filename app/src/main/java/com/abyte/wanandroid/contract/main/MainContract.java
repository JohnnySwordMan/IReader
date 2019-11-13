package com.abyte.wanandroid.contract.main;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;

/**
 * MainActivity对应的Contract
 * Created by geyan on 2018
 */
public interface MainContract {

    interface View extends AbstractView{

    }

    interface Presenter extends AbstractPresenter<View>{

        // 设置当前page
        void setCurrentPage(int page);
    }
}
