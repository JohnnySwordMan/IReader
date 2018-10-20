package com.jaygege.smartx.core.bean.home.collect;

import java.util.List;

/**
 * Created by geyan on 2018/9/20
 */
public class FeedArticleListEntity {

    public int curPage;
    public List<FeedArticleEntity> datas;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
}
