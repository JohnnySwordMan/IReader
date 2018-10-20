package com.jaygege.smartx.contract.main;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;

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
