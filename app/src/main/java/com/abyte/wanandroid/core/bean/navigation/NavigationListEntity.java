package com.abyte.wanandroid.core.bean.navigation;

import com.abyte.wanandroid.base.BaseObject;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class NavigationListEntity extends BaseObject implements Serializable {

    public int cid;
    public String name;
    public List<FeedArticleEntity> articles;
}
