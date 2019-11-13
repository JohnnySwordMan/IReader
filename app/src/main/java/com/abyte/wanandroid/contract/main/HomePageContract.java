package com.abyte.wanandroid.contract.main;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.home.banner.BannerEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/20
 */
public interface HomePageContract {

    interface View extends AbstractView {

        void setData(List<BannerEntity> bannerEntityList, FeedArticleListEntity feedArticleListEntity);

        void setData(FeedArticleListEntity feedArticleListEntity);

        void onSuccess();

        void onFailure();
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 加载首页数据
         */
        void loadHomePageData();
    }
}
