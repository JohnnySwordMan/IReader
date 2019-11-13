package com.abyte.wanandroid.network;

import com.abyte.wanandroid.base.response.BaseResponse;
import com.abyte.wanandroid.core.bean.LoginEntity;
import com.abyte.wanandroid.core.bean.choose.KnowledgeHierarchyEntity;
import com.abyte.wanandroid.core.bean.home.banner.BannerEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;
import com.abyte.wanandroid.core.bean.navigation.NavigationListEntity;
import com.abyte.wanandroid.core.bean.project.ProjectListEntity;
import com.abyte.wanandroid.core.bean.project.ProjectTabEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by geyan on 2018/9/20
 */
public interface HttpService {

    /**
     * http://www.wanandroid.com/banner/json
     */
    // 首页-banner
    @GET("banner/json")
    Observable<BaseResponse<List<BannerEntity>>> getBannerData();

    // 首页列表数据
    @GET("article/list/{num}/json")
    Observable<BaseResponse<FeedArticleListEntity>> getFeedArticleList(@Path("num") int num);

    // 知识体系列表
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeHierarchyEntity>>> getKnowledgeHierarchyList();

    // 项目tab列表
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectTabEntity>>> getProjectTabList();

    // 项目tab对应的列表数据
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectListEntity>> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    // 导航数据
    @GET("navi/json")
    Observable<BaseResponse<List<NavigationListEntity>>> getNavigationList();

    // 知识体系-点击进入item
    @GET("article/list/{page}/json")
    Observable<BaseResponse<FeedArticleListEntity>> getKnowledgeHierarchyListData(@Path("page") int page, @Query("cid") int cid);

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * Field一般用于POST请求数据，需要加@FormUrlEncoded，不然会报IllegalArgumentException异常
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginEntity>> getLoginData(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginEntity>> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


}
