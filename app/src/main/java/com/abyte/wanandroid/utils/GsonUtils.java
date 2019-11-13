package com.abyte.wanandroid.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.abyte.wanandroid.base.BaseObject;

import java.lang.reflect.Type;

/**
 * Created by geyan on 2018/9/29
 */
public class GsonUtils {

    public static Gson mGson = new Gson();

    public static <T> String toJson(T data) {
        if (data != null) {
            return mGson.toJson(data);
        }
        return null;
    }

    public static <T extends BaseObject> T fromJson(String jsonStr, Type type) {
        if (!TextUtils.isEmpty(jsonStr) && type != null) {
            return mGson.fromJson(jsonStr, type);
        }
        return null;
    }
}
