package com.jaygege.smartx.core.httpUseCase.choose;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import rx.Observable;

/**
 * 知识体系列表-点击item-每个item对应的列表页
 * Created by geyan on 2018/9/30
 */
public class GetKnowledgeHierarchyListDataFromNet extends SimpleNetRxHttp {

    private int currentPage;
    private int cid;


    public void setRequest(int currentPage, int cid) {
        this.currentPage = currentPage;
        this.cid = cid;
    }

    @Override
    protected Observable<FeedArticleListEntity> create() {
        Observable<FeedArticleListEntity> entity = RetrofitUtils.getHttpService().getKnowledgeHierarchyListData(currentPage, cid).map(new BaseResponseFunc<>());
        return entity;
    }
}
