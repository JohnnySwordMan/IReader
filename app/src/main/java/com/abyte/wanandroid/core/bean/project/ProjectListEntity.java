package com.abyte.wanandroid.core.bean.project;

import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectListEntity {

    public int curPage;
    public List<FeedArticleEntity> datas;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
}
