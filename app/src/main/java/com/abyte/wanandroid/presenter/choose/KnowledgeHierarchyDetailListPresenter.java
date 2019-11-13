package com.abyte.wanandroid.presenter.choose;


import com.abyte.wanandroid.base.Constant;
import com.abyte.wanandroid.base.DataManager;
import com.abyte.wanandroid.base.presenter.BasePresenter;
import com.abyte.wanandroid.contract.choose.KnowledgeHierarchyDetailListContract;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;
import com.abyte.wanandroid.ui.choose.adapter.KnowledgeHierarchyDetailListAdapter;
import com.abyte.wanandroid.utils.RxJavaUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Created by geyan on 2018/9/29
 */
public class KnowledgeHierarchyDetailListPresenter extends BasePresenter<KnowledgeHierarchyDetailListContract.View> implements KnowledgeHierarchyDetailListContract.Presenter {

    private int currentPage = 0;
    private int cid;
    private KnowledgeHierarchyDetailListAdapter mAdapter;
    private final DataManager dataManager;
    private FeedArticleListEntity entity;


    public KnowledgeHierarchyDetailListPresenter(KnowledgeHierarchyDetailListAdapter adapter) {
        this.mAdapter = adapter;
        dataManager = DataManager.getInstance();
    }

    @Override
    public void setCurrentPage(int currentPage, int cid) {
        this.currentPage = currentPage;
        this.cid = cid;
    }

    @Override
    public void loadKnowledgeHierarchyDetailListData() {
        getKnowledgeHierarchyListDataFromNet();
    }

    private void getKnowledgeHierarchyListDataFromNet() {

        Disposable subscriber = dataManager.getHttpService().getKnowledgeHierarchyListData(currentPage, cid)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<FeedArticleListEntity>() {
                    @Override
                    public void accept(FeedArticleListEntity feedArticleListEntity) throws Exception {
                        getView().showData(feedArticleListEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addSubscriber(subscriber);

//        mGetknowledgeHierarchyListDataFromNet.setRequest(currentPage, cid);
//        mGetknowledgeHierarchyListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(FeedArticleListEntity feedArticleListEntity) {
//                getView().showData(feedArticleListEntity);
//            }
//        });
    }

    @Override
    public void setRefreshData() {
        // 将当前页置为0，重新请求数据
        currentPage = 0;
        getKnowledgeHierarchyListDataFromNet();
    }

    @Override
    public void loadMoreData() {
        mAdapter.setLoadingState(Constant.LOADING);
        currentPage++;

        Disposable subscriber = dataManager.getHttpService().getKnowledgeHierarchyListData(currentPage, cid)
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<FeedArticleListEntity>() {
                    @Override
                    public void accept(FeedArticleListEntity feedArticleListEntity) throws Exception {
                        entity = feedArticleListEntity;
                        getView().showData(feedArticleListEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (entity != null) {
                            mAdapter.setLoadingState(entity.over ? Constant.LOADING_END : Constant.LOADING_COMPLETE);
                        }
                    }
                });
        addSubscriber(subscriber);

//        mGetknowledgeHierarchyListDataFromNet.setRequest(currentPage, cid);
//        mGetknowledgeHierarchyListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {
//
//            private FeedArticleListEntity entity;
//
//            @Override
//            public void onCompleted() {
//                if (entity != null) {
//                    mAdapter.setLoadingState(entity.over ? Constant.LOADING_END : Constant.LOADING_COMPLETE);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(FeedArticleListEntity feedArticleListEntity) {
//                entity = feedArticleListEntity;
//                getView().showData(feedArticleListEntity);
//            }
//        });
    }

}
