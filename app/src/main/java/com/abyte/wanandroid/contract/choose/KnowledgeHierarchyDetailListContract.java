package com.abyte.wanandroid.contract.choose;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;

/**
 * Created by geyan on 2018/9/29
 */
public interface KnowledgeHierarchyDetailListContract {

    interface View extends AbstractView{

        void showData(FeedArticleListEntity feedArticleListEntity);

    }

    interface Presenter extends AbstractPresenter<View>{
        void setCurrentPage(int currentPage,int cid);
        void loadKnowledgeHierarchyDetailListData();
        // 下拉刷新
        void setRefreshData();
        // 上拉加载更多
        void loadMoreData();
    }
}
