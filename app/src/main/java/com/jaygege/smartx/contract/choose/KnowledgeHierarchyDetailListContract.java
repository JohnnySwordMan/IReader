package com.jaygege.smartx.contract.choose;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;

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
