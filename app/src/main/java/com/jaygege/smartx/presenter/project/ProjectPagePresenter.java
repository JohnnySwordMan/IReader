package com.jaygege.smartx.presenter.project;

import android.util.Log;

import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.project.ProjectPageContract;
import com.jaygege.smartx.core.bean.project.ProjectTabEntity;
import com.jaygege.smartx.core.httpUseCase.project.GetProjectTabDataFromNet;

import java.util.List;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectPagePresenter extends BasePresenter<ProjectPageContract.View> implements ProjectPageContract.Presenter {

    private final GetProjectTabDataFromNet mGetProjectTabDataFromNet;

    public ProjectPagePresenter() {
        mGetProjectTabDataFromNet = new GetProjectTabDataFromNet();
    }


    @Override
    public void loadTabData() {
        // 进行网络请求
        getProjectTabList();
    }

    private void getProjectTabList() {
        mGetProjectTabDataFromNet.execute(new Subscriber<List<ProjectTabEntity>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(List<ProjectTabEntity> projectTabEntities) {
                getView().updateTabTitles(projectTabEntities);
            }
        });
    }
}
