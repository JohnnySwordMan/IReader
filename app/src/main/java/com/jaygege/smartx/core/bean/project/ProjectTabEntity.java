package com.jaygege.smartx.core.bean.project;

import java.io.Serializable;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectTabEntity implements Serializable {

    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public int visible;
}
