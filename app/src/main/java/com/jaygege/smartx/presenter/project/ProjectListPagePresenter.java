package com.jaygege.smartx.presenter.project;


import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.DataManager;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.project.ProjectListPageContract;
import com.jaygege.smartx.core.bean.project.ProjectListEntity;
import com.jaygege.smartx.ui.project.adapter.ProjectListAdapter;
import com.jaygege.smartx.utils.RxJavaUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectListPagePresenter extends BasePresenter<ProjectListPageContract.View> implements ProjectListPageContract.Presenter {

    private int mCurrentPage = 0;
    private int cid;
    private ProjectListAdapter mAdapter;
    private final DataManager dataManager;
    private ProjectListEntity entity;


    public ProjectListPagePresenter(ProjectListAdapter adapter) {
        this.mAdapter = adapter;
        dataManager = DataManager.getInstance();
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

        Disposable subscriber = dataManager.getHttpService().getProjectListData(mCurrentPage, cid)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<ProjectListEntity>() {
                    @Override
                    public void accept(ProjectListEntity projectListEntity) throws Exception {
                        entity = projectListEntity;
                        getView().setData(projectListEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (entity.over) {
                            mAdapter.setLoadingState(Constant.LOADING_END);
                        } else {
                            mAdapter.setLoadingState(Constant.LOADING_COMPLETE);
                        }
                    }
                });
        addSubscriber(subscriber);

//        mGetProjectListDataFromNet.setRequest(mCurrentPage, cid);
//        mGetProjectListDataFromNet.execute(new Subscriber<ProjectListEntity>() {
//
//            private ProjectListEntity entity;
//
//            @Override
//            public void onCompleted() {
//                if (entity.over) {
//                    mAdapter.setLoadingState(Constant.LOADING_END);
//                } else {
//                    mAdapter.setLoadingState(Constant.LOADING_COMPLETE);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(ProjectListEntity projectListEntity) {
//                entity = projectListEntity;
//                getView().setData(projectListEntity);
//            }
//        });
    }

    private void getProjectListDataFromNet() {

        Disposable subscriber = dataManager.getHttpService().getProjectListData(mCurrentPage, cid)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<ProjectListEntity>() {
                    @Override
                    public void accept(ProjectListEntity projectListEntity) throws Exception {
                        getView().setData(projectListEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onFailure();
                    }
                });
        addSubscriber(subscriber);

//        mGetProjectListDataFromNet.setRequest(mCurrentPage, cid);
//        mGetProjectListDataFromNet.execute(new Subscriber<ProjectListEntity>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                getView().onFailure();
//            }
//
//            @Override
//            public void onNext(ProjectListEntity projectListEntity) {
//                getView().setData(projectListEntity);
//            }
//        });
    }
}
