package com.jaygege.smartx.contract.choose;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;

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
