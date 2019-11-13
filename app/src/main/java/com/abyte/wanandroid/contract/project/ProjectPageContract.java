package com.abyte.wanandroid.contract.project;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.project.ProjectTabEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public interface ProjectPageContract {

    interface View extends AbstractView {
        void updateTabTitles(List<ProjectTabEntity> list);
        void onFailure();
    }

    interface Presenter extends AbstractPresenter<View> {
        void loadTabData();
    }
}
