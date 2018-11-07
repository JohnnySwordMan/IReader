package com.jaygege.smartx.presenter.main;

import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.main.HomePageContract;
import com.jaygege.smartx.core.bean.home.banner.BannerEntity;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;
import com.jaygege.smartx.core.httpUseCase.home.GetArticleListDataFromNet;
import com.jaygege.smartx.core.httpUseCase.home.GetBannerDataFromNet;
import com.jaygege.smartx.ui.main.adapter.FeedArticleListAdapter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/20
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {


    public GetBannerDataFromNet mGetBannerDataFromNet;
    public GetArticleListDataFromNet mGetArticleListDataFromNet;
    private boolean isRefresh = true;
    // 当前页
    private int mCurrentPage = 0;
    private List<BannerEntity> mBannerEntityList;
    private FeedArticleListAdapter mAdapter;

    public HomePagePresenter(FeedArticleListAdapter adapter) {
        this.mAdapter = adapter;
        mGetBannerDataFromNet = new GetBannerDataFromNet();
        mGetArticleListDataFromNet = new GetArticleListDataFromNet();
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }


    @Override
    public void loadHomePageData() {
        // 请求banner数据和列表数据
        getBannerDataFromNet();
    }

    private void getArticleListDataFromNet() {
        // 设置当前页
        mGetArticleListDataFromNet.setRequest(mCurrentPage);
        mGetArticleListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(FeedArticleListEntity feedArticleListEntity) {
                getView().onSuccess();
                getView().setData(mBannerEntityList, feedArticleListEntity);
            }
        });
    }

    /**
     * 请求banner数据
     */
    private void getBannerDataFromNet() {
        mGetBannerDataFromNet.execute(new Subscriber<List<BannerEntity>>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(List<BannerEntity> bannerEntityList) {
                mBannerEntityList = bannerEntityList;
                getArticleListDataFromNet();
            }
        });
    }

    public void setRefresh() {
        isRefresh = true;
        // 下拉刷新，需要将当前页置为0
        mCurrentPage = 0;
        getBannerDataFromNet();
    }

    /**
     * 上拉加载
     */
    public void loadMoreData() {
        mAdapter.setLoadingState(Constant.LOADING);
        isRefresh = false;
        mCurrentPage++;
        // 设置当前页
        mGetArticleListDataFromNet.setRequest(mCurrentPage);
        mGetArticleListDataFromNet.execute(new Subscriber<FeedArticleListEntity>() {

            private FeedArticleListEntity entity;

            @Override
            public void onCompleted() {
                mAdapter.setLoadingState(Constant.LOADING_COMPLETE);
                if (entity != null) {
                    mAdapter.setLoadingState(entity.over ? Constant.LOADING_END : Constant.LOADING_COMPLETE);
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(FeedArticleListEntity feedArticleListEntity) {
                getView().onSuccess();
                entity = feedArticleListEntity;
                getView().setData(feedArticleListEntity);
            }
        });
    }
}
