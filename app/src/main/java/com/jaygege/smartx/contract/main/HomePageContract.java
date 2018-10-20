package com.jaygege.smartx.contract.main;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.home.banner.BannerEntity;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;

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
