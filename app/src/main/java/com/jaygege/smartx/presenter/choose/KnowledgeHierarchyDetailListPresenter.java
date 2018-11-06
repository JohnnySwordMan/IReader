package com.jaygege.smartx.presenter.choose;

import android.util.Log;

import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.choose.KnowledgeHierarchyDetailListContract;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;
import com.jaygege.smartx.core.httpUseCase.choose.GetKnowledgeHierarchyListDataFromNet;
import com.jaygege.smartx.ui.choose.adapter.KnowledgeHierarchyDetailListAdapter;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/29
 */
public class KnowledgeHierarchyDetailListPresenter extends BasePresenter<KnowledgeHierarchyDetailListContract.View> implements KnowledgeHierarchyDetailListContract.Presenter {

    private int currentPage = 0;
    private int cid;
    private final GetKnowledgeHierarchyListDataFromNet mGetknowledgeHierarchyListDataFromNet;
    private KnowledgeHierarchyDetailListAdapter mAdapter;


    public KnowledgeHierarchyDetailListPresenter(KnowledgeHierarchyDetailListAdapter adapter) {
        this.mAdapter = adapter;
        mGetknowledgeHierarchyListDataFromNet = new GetKnowledgeHierarchyListDataFromNet();
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
        mGetknowledgeHierarchyListDataFromNet.setRequest(currentPage, cid);
        mGetknowledgeHierarchyListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FeedArticleListEntity feedArticleListEntity) {
                getView().showData(feedArticleListEntity);
            }
        });
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
        mGetknowledgeHierarchyListDataFromNet.setRequest(currentPage, cid);
        mGetknowledgeHierarchyListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {

            private FeedArticleListEntity entity;

            @Override
            public void onCompleted() {
                if (entity != null) {
                    mAdapter.setLoadingState(entity.over ? Constant.LOADING_END : Constant.LOADING_COMPLETE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FeedArticleListEntity feedArticleListEntity) {
                entity = feedArticleListEntity;
                getView().showData(feedArticleListEntity);
            }
        });
    }

}
