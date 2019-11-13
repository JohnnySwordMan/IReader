package com.abyte.wanandroid.base.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Jaygege on 2018/9/25
 */
public abstract class LoadMoreOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isScrollUp = false;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // dy大于0，表示正在向上滑动；小于等于0，表示停止或者向下滑
        isScrollUp = dy > 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 在不滑动的前提下
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 获取最后一个完全显示的itemPosition
            int lastItemPos = layoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = layoutManager.getItemCount();
            // 判断是否已经滑动到最后一个item，且此时是向上滑动
            if (lastItemPos + 1 == itemCount && isScrollUp) {
                loadMoreData();
            }
        }
    }

    public abstract void loadMoreData();
}
