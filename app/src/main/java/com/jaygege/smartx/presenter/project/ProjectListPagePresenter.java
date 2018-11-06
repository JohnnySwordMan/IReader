package com.jaygege.smartx.presenter.project;

import android.util.Log;

import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.project.ProjectListPageContract;
import com.jaygege.smartx.core.bean.project.ProjectListEntity;
import com.jaygege.smartx.core.httpUseCase.project.GetProjectListDataFromNet;
import com.jaygege.smartx.ui.project.adapter.ProjectListAdapter;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectListPagePresenter extends BasePresenter<ProjectListPageContract.View> implements ProjectListPageContract.Presenter {

    private final GetProjectListDataFromNet mGetProjectListDataFromNet;
    private int mCurrentPage = 0;
    private int cid;
    private ProjectListAdapter mAdapter;


    public ProjectListPagePresenter(ProjectListAdapter adapter) {
        this.mAdapter = adapter;
        mGetProjectListDataFromNet = new GetProjectListDataFromNet();
    }

    public void setCurrentPage(int currentPage, int id) {
        this.mCurrentPage = currentPage;
        this.cid = id;
    }

    @Override
    public void loadProjectListData() {
        // 请求列表数据
        getProjectListDataFromNet();
    }

    @Override
    public void setRefreshData() {
        mCurrentPage = 0;
        getProjectListDataFromNet();
    }

    @Override
    public void loadMoreData() {
        mAdapter.setLoadingState(Constant.LOADING);
        mCurrentPage++;
        mGetProjectListDataFromNet.setRequest(mCurrentPage, cid);
        mGetProjectListDataFromNet.execute(new Subscriber<ProjectListEntity>() {

            private ProjectListEntity entity;

            @Override
            public void onCompleted() {
                if (entity.over) {
                    mAdapter.setLoadingState(Constant.LOADING_END);
                } else {
                    mAdapter.setLoadingState(Constant.LOADING_COMPLETE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProjectListEntity projectListEntity) {
                entity = projectListEntity;
                getView().setData(projectListEntity);
            }
        });
    }

    private void getProjectListDataFromNet() {
        mGetProjectListDataFromNet.setRequest(mCurrentPage, cid);
        mGetProjectListDataFromNet.execute(new Subscriber<ProjectListEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(ProjectListEntity projectListEntity) {
                getView().setData(projectListEntity);
            }
        });
    }
}
