package com.jaygege.smartx.core.bean;

import com.jaygege.smartx.base.BaseObject;

import java.util.List;

/**
 * Created by Jaygege on 2018/10/18
 */
public class LoginEntity extends BaseObject{

    public String username;

    public String password;

    public String icon;

    public int type;

    public List<Integer> collectIds;
}
