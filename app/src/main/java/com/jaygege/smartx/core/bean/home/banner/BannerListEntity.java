package com.jaygege.smartx.core.bean.home.banner;

import com.jaygege.smartx.core.bean.home.HomeItem;

import java.util.List;

/**
 * Created by geyan on 2018/9/26
 */
public class BannerListEntity implements HomeItem {

    public List<BannerEntity> mBannerListEntity;

    @Override
    public int getItemType() {
        return HomeItem.TYPE_BANNER;
    }
}
