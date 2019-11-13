package com.abyte.wanandroid.core.bean.choose;

import com.abyte.wanandroid.base.BaseObject;

import java.io.Serializable;
import java.util.List;

/**
 * 知识体系-服务端返回的实体数据
 * Created by geyan on 2018/9/26
 */

public class KnowledgeHierarchyEntity extends BaseObject implements Serializable {

    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public int visible;

    public List<KnowledgeHierarchyEntity> children;
}
