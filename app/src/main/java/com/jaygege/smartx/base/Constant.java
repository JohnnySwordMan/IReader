package com.jaygege.smartx.base;

/**
 * Created by geyan on 2018/9/20
 */
public class Constant {

    public static final int TYPE_HOME_PAGE = 0;
    public static final int TYPE_HOME_CHOOSE = 1;
    public static final int TYPE_HOME_PIC = 2;
    public static final int TYPE_HOME_ME = 3;

    public static final String BANNER_ENTITY = "banner_entity";

    public static final String ARTICLE_ENTITY = "article_entity";

    public static final String BASE_URL = "http://www.wanandroid.com/";

    // 跳转到ArticleDetailActivity，所需要的参数
    public static final String ARTICLE_ID = "article_id";
    public static final String ARTICLE_TITLE = "article_title";
    public static final String ARTICLE_LINK = "article_link";
    public static final String IS_COLLECT = "is_collect";
    public static final String IS_COLLECTY_PAGE = "is_collect_page";
    public static final String IS_COLLECT_SIZE = "is_collect_size";
    public static final String IS_COMMON_SITE = "is_common_site";

    // 正在加载状态
    public static final int LOADING = 1;
    // 加载完成状态
    public static final int LOADING_COMPLETE = 2;
    // 加载结束状态
    public static final int LOADING_END = 3;

}
