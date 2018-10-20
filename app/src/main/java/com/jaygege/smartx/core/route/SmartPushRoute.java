package com.jaygege.smartx.core.route;

import android.content.Context;
import android.content.Intent;

import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.ui.main.activity.ArticleDetailActivity;

/**
 * Created by geyan on 2018/9/23
 * 可简单认为是SmartX项目中的路由跳转
 */
public class SmartPushRoute {

    /**
     * 跳转到文章详情页面
     */
    public static void startArticleDetailActivity(Context context, int articleId, String articleTitle, String articleLink,
                                                  boolean isCollect, boolean isCollectPage, boolean isCommonSite) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constant.ARTICLE_ID, articleId);
        intent.putExtra(Constant.ARTICLE_TITLE, articleTitle);
        intent.putExtra(Constant.ARTICLE_LINK, articleLink);
        intent.putExtra(Constant.IS_COLLECT, isCollect);
        intent.putExtra(Constant.IS_COLLECTY_PAGE, isCollectPage);
        intent.putExtra(Constant.IS_COMMON_SITE, isCommonSite);
        context.startActivity(intent);
    }
}
