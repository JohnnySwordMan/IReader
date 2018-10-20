package com.jaygege.smartx.core.httpUseCase.choose;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import java.util.List;

import rx.Observable;

/**
 * 知识体系-网络请求
 * Created by geyan on 2018/9/26
 */
public class GetKnowledgeHierarchyDataFromNet extends SimpleNetRxHttp {

    public GetKnowledgeHierarchyDataFromNet() {

    }

    @Override
    protected Observable<List<KnowledgeHierarchyEntity>> create() {
        Observable<List<KnowledgeHierarchyEntity>> knowledgeHierarchyEntityList = RetrofitUtils.getHttpService().getKnowledgeHierarchyList().map(new BaseResponseFunc<>());
        return knowledgeHierarchyEntityList;
    }
}
