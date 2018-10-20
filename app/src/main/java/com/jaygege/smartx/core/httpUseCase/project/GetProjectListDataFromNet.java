package com.jaygege.smartx.core.httpUseCase.project;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.project.ProjectListEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import rx.Observable;

/**
 * Created by geyan on 2018/9/27
 */
public class GetProjectListDataFromNet extends SimpleNetRxHttp {

    private int pageNum;
    private int cid;

    public GetProjectListDataFromNet() {

    }

    public void setRequest(int pageNum, int cid) {
        this.pageNum = pageNum;
        this.cid = cid;
    }

    @Override
    protected Observable<ProjectListEntity> create() {
        Observable<ProjectListEntity> projectListEntity = RetrofitUtils.getHttpService().getProjectListData(pageNum, cid).map(new BaseResponseFunc<>());
        return projectListEntity;
    }
}
