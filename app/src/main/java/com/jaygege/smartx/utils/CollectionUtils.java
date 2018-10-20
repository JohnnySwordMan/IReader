package com.jaygege.smartx.utils;

import java.util.Collection;

/**
 * Created by geyan on 2018/9/21
 * 判断集合是否为空
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
