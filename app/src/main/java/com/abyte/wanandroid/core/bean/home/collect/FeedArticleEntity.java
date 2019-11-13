package com.abyte.wanandroid.core.bean.home.collect;

import com.abyte.wanandroid.base.BaseObject;
import com.abyte.wanandroid.core.bean.home.HomeItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by geyan on 2018/9/20
 */
public class FeedArticleEntity extends BaseObject implements Serializable, HomeItem {

    public String apkLink;

    public String author;

    public int chapterId;

    public String chapterName;

    public boolean collect;

    public int courseId;

    public String desc;

    public String envelopePic;

    public boolean fresh;

    public int id;

    public String link;

    public String niceDate;

    public String origin;

    public String projectLink;

    public long publishTime;

    public int superChapterId;

    public String superChapterName;

    public String title;

    public int type;

    public int userId;

    public int visible;

    public int zan;

    public List<Tags> tags;

    @Override
    public int getItemType() {
        return HomeItem.TYPE_NORMAL;
    }


    public static class Tags implements Serializable {
        public String name;
        public String url;
    }
}
