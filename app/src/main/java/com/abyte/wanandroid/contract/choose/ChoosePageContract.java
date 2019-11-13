package com.abyte.wanandroid.contract.choose;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.core.bean.choose.KnowledgeHierarchyEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/26
 */
public interface ChoosePageContract {

    interface View extends AbstractView {
        void setData(List<KnowledgeHierarchyEntity> list);

        void onFailure();
    }

    interface Presenter extends AbstractPresenter<View> {
        void loadChoosePageData();

        void setRefresh();
    }
}
