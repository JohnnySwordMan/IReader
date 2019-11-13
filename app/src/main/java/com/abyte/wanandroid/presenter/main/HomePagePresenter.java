package com.abyte.wanandroid.presenter.main;

import android.support.annotation.NonNull;

import com.abyte.wanandroid.base.Constant;
import com.abyte.wanandroid.base.DataManager;
import com.abyte.wanandroid.base.presenter.BasePresenter;
import com.abyte.wanandroid.base.response.BaseResponse;
import com.abyte.wanandroid.contract.main.HomePageContract;
import com.abyte.wanandroid.core.bean.home.banner.BannerEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;
import com.abyte.wanandroid.ui.main.adapter.FeedArticleListAdapter;
import com.abyte.wanandroid.utils.RxJavaUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by geyan on 2018/9/20
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {


    private boolean isRefresh = true;
    // 当前页
    private int mCurrentPage = 0;
    private FeedArticleListAdapter mAdapter;
    private final DataManager mDataManager;
    private FeedArticleListEntity entity;

    public HomePagePresenter(FeedArticleListAdapter adapter) {
        this.mAdapter = adapter;
        mDataManager = DataManager.getInstance();
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }


    @Override
    public void loadHomePageData() {
        Observable<BaseResponse<List<BannerEntity>>> bannerData = mDataManager.getHttpService().getBannerData();
        Observable<BaseResponse<FeedArticleListEntity>> feedArticleList = mDataManager.getHttpService().getFeedArticleList(mCurrentPage);
        Disposable subscriber = Observable.zip(bannerData, feedArticleList, (bannerResponse, feedArticleListResponse) -> createResponseMap(bannerResponse,
                feedArticleListResponse)).compose(RxJavaUtils.applySchedulers())
                .subscribe(new Consumer<HashMap<String, Object>>() {
                               @Override
                               public void accept(HashMap<String, Object> map) throws Exception {
                                   BaseResponse<List<BannerEntity>> bannerDataResponse = (BaseResponse<List<BannerEntity>>) map.get("banner_data");
                                   BaseResponse<FeedArticleListEntity> feedArticleListEntityResponse = (BaseResponse<FeedArticleListEntity>) map.get("article_data");
                                   if (bannerDataResponse != null && bannerDataResponse.getData() != null && feedArticleListEntityResponse != null
                                           && feedArticleListEntityResponse.getData() != null) {
                                       getView().onSuccess();
                                       getView().setData(bannerDataResponse.getData(), feedArticleListEntityResponse.getData());
                                   }
                               }
                           }, throwable -> getView().onFailure()
                );
        addSubscriber(subscriber);
    }

    /**
     * 需要将banner数据和文章list数据整合到一起
     */
    @NonNull
    private HashMap<String, Object> createResponseMap(
            BaseResponse<List<BannerEntity>> bannerResponse,
            BaseResponse<FeedArticleListEntity> feedArticleListResponse) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("banner_data", bannerResponse);
        map.put("article_data", feedArticleListResponse);
        return map;
    }

    public void setRefresh() {
        isRefresh = true;
        // 下拉刷新，需要将当前页置为0
        mCurrentPage = 0;
        loadHomePageData();
    }

    /**
     * 上拉加载
     */
    public void loadMoreData() {
        mAdapter.setLoadingState(Constant.LOADING);
        isRefresh = false;
        mCurrentPage++;

        Disposable subscribe = mDataManager.getHttpService().getFeedArticleList(mCurrentPage).compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<FeedArticleListEntity>() {
                    @Override
                    public void accept(FeedArticleListEntity feedArticleListEntity) throws Exception {
                        getView().onSuccess();
                        entity = feedArticleListEntity;
                        getView().setData(feedArticleListEntity);
                    }
                }, throwable -> getView().onFailure(), () -> {
                    mAdapter.setLoadingState(Constant.LOADING_COMPLETE);
                    if (entity != null) {
                        mAdapter.setLoadingState(entity.over ? Constant.LOADING_END : Constant.LOADING_COMPLETE);
                    }
                });
        addSubscriber(subscribe);
    }
}
