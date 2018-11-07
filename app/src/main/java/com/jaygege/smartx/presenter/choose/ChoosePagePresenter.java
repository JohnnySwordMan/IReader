package com.jaygege.smartx.presenter.choose;

import com.jaygege.smartx.base.DataManager;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.choose.ChoosePageContract;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;
import com.jaygege.smartx.utils.RxJavaUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ChoosePagePresenter extends BasePresenter<ChoosePageContract.View> implements ChoosePageContract.Presenter {
    private final DataManager dataManager;

//    private final GetKnowledgeHierarchyDataFromNet mGetKnowledgeHierarchyDataFromNet;

    public ChoosePagePresenter() {
//        mGetKnowledgeHierarchyDataFromNet = new GetKnowledgeHierarchyDataFromNet();
        dataManager = DataManager.getInstance();
    }

    @Override
    public void loadChoosePageData() {
        // 请求知识体系列表数据
        getKnowledgeHierarchyData();
    }

    @Override
    public void setRefresh() {
        getKnowledgeHierarchyData();
    }

    private void getKnowledgeHierarchyData() {

        Disposable subscriber = dataManager.getHttpService().getKnowledgeHierarchyList().compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<List<KnowledgeHierarchyEntity>>() {
                    @Override
                    public void accept(List<KnowledgeHierarchyEntity> knowledgeHierarchyEntities) throws Exception {
                        getView().setData(knowledgeHierarchyEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onFailure();
                    }
                });
        addSubscriber(subscriber);

//        mGetKnowledgeHierarchyDataFromNet.execute(new Subscriber<List<KnowledgeHierarchyEntity>>() {
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
//            public void onNext(List<KnowledgeHierarchyEntity> knowledgeHierarchyEntities) {
//                getView().setData(knowledgeHierarchyEntities);
//            }
//        });
    }
}
