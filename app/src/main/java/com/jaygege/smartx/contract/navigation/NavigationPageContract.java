package com.jaygege.smartx.contract.navigation;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.navigation.NavigationListEntity;

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
