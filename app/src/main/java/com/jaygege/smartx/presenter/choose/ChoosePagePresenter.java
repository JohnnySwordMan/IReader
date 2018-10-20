package com.jaygege.smartx.presenter.choose;

import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.choose.ChoosePageContract;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;
import com.jaygege.smartx.core.httpUseCase.choose.GetKnowledgeHierarchyDataFromNet;

import java.util.List;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/26
 */
public class ChoosePagePresenter extends BasePresenter<ChoosePageContract.View> implements ChoosePageContract.Presenter {

    private final GetKnowledgeHierarchyDataFromNet mGetKnowledgeHierarchyDataFromNet;

    public ChoosePagePresenter() {
        mGetKnowledgeHierarchyDataFromNet = new GetKnowledgeHierarchyDataFromNet();
    }

    @Override
    public void loadChoosePageData() {
        // 请求知识体系列表数据
        getKnowledgeHierarchyData();
    }

    @Override
    public void setRefresh() {
        getKnowledgeHierarchyData();
    }

    private void getKnowledgeHierarchyData() {
        mGetKnowledgeHierarchyDataFromNet.execute(new Subscriber<List<KnowledgeHierarchyEntity>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.onFailure();
            }

            @Override
            public void onNext(List<KnowledgeHierarchyEntity> knowledgeHierarchyEntities) {
                mView.setData(knowledgeHierarchyEntities);
            }
        });
    }
}
