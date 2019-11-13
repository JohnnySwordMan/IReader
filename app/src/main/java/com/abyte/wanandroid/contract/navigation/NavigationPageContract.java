package com.abyte.wanandroid.contract.navigation;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.navigation.NavigationListEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public interface NavigationPageContract {

    interface View extends AbstractView {

        void showNavigationData(List<NavigationListEntity> list);

        void onFailure();
    }

    interface Presenter extends AbstractPresenter<View> {
        void loadNavigationData();
    }
}
