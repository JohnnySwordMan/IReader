package com.jaygege.smartx.base.listener;

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
        isScrollUp = dy > 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 不滑动时
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
