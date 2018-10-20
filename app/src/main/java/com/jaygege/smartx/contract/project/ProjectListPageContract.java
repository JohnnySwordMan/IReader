package com.jaygege.smartx.contract.project;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.project.ProjectListEntity;

/**
 * Created by geyan on 2018/9/27
 */
public interface ProjectListPageContract {

    interface View extends AbstractView {

        void setData(ProjectListEntity entity);

        void onFailure();
    }

    interface Presenter extends AbstractPresenter<View> {

        void setCurrentPage(int currentPage,int cid);

        void loadProjectListData();

        void setRefreshData();

        void loadMoreData();
    }
}
