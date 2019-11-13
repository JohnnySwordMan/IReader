package com.abyte.wanandroid.ui.me;

/**
 * Created by geyan on 2018
 */
public class LoginMessageEvent {

    private int code;
    private String userName;

    public LoginMessageEvent(int code,String userName) {
        this.code = code;
        this.userName = userName;
    }

    public int getCode(){
        return code;
    }

    public String getUserName(){
        return userName;
    }
}
