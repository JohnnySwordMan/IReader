package com.jaygege.smartx.core.httpUseCase.project;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.project.ProjectTabEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import java.util.List;

import rx.Observable;

/**
 * 项目-tab
 * Created by geyan on 2018/9/27
 */
public class GetProjectTabDataFromNet extends SimpleNetRxHttp {

    public GetProjectTabDataFromNet() {
    }

    @Override
    protected Observable<List<ProjectTabEntity>> create() {
        Observable<List<ProjectTabEntity>> projectTabList = RetrofitUtils.getHttpService().getProjectTabList().map(new BaseResponseFunc<>());
        return projectTabList;
    }
}
