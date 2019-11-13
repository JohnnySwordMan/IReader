package com.abyte.wanandroid.presenter.project;


import com.abyte.wanandroid.base.DataManager;
import com.abyte.wanandroid.base.presenter.BasePresenter;
import com.abyte.wanandroid.contract.project.ProjectPageContract;
import com.abyte.wanandroid.core.bean.project.ProjectTabEntity;
import com.abyte.wanandroid.utils.RxJavaUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by geyan on 2018/9/27
 */
public class ProjectPagePresenter extends BasePresenter<ProjectPageContract.View> implements ProjectPageContract.Presenter {

    private final DataManager dataManager;

    public ProjectPagePresenter() {
//        mGetProjectTabDataFromNet = new GetProjectTabDataFromNet();
        dataManager = DataManager.getInstance();
    }


    @Override
    public void loadTabData() {
        // 进行网络请求
        getProjectTabList();
    }

    private void getProjectTabList() {

        Disposable subscriber = dataManager.getHttpService().getProjectTabList().compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<List<ProjectTabEntity>>() {
                    @Override
                    public void accept(List<ProjectTabEntity> projectTabEntities) throws Exception {
                        getView().updateTabTitles(projectTabEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onFailure();
                    }
                });
        addSubscriber(subscriber);

//        mGetProjectTabDataFromNet.execute(new Subscriber<List<ProjectTabEntity>>() {
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
//            public void onNext(List<ProjectTabEntity> projectTabEntities) {
//                getView().updateTabTitles(projectTabEntities);
//            }
//        });
    }
}
