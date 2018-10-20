package com.jaygege.smartx.contract.choose;

import com.jaygege.smartx.base.presenter.AbstractPresenter;
import com.jaygege.smartx.base.view.AbstractView;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/29
 */
public interface KnowledgeHierarchyDetailContract {

    interface View extends AbstractView {
//        void updateTabTitles(List<KnowledgeHierarchyEntity> list);
    }

    interface Presenter extends AbstractPresenter<View> {

    }
}
