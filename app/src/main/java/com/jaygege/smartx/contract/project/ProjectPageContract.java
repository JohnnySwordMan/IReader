package com.jaygege.smartx.contract.project;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.project.ProjectTabEntity;

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
