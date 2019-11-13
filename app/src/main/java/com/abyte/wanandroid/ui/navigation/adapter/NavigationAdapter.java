package com.abyte.wanandroid.ui.navigation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;
import com.abyte.wanandroid.core.bean.navigation.NavigationListEntity;
import com.abyte.wanandroid.core.route.SmartPushRoute;
import com.abyte.wanandroid.utils.CollectionUtils;
import com.abyte.wanandroid.utils.CommonUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<NavigationListEntity> navEntityList = new ArrayList<>();
    private static final int TYPE_NORMAL = 1;

    public NavigationAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(NavigationListEntity entity) {
        navEntityList.clear();
        if (entity != null) {
            navEntityList.add(entity);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return navEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_navigation_page, parent, false);
            return new NavigationViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NavigationViewHolder) {
            NavigationViewHolder navigationViewHolder = (NavigationViewHolder) holder;
            navigationViewHolder.onBindData(position);
        }
    }

    class NavigationViewHolder extends RecyclerView.ViewHolder implements TagFlowLayout.OnTagClickListener {

        TextView mTvText;
        TagFlowLayout mFlowLayout;
        List<FeedArticleEntity> articles;

        NavigationViewHolder(View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.navigation_page_item_text);
            mFlowLayout = itemView.findViewById(R.id.navigation_page_item_flow_layout);
            mFlowLayout.setOnTagClickListener(this);
        }

        public void onBindData(int position) {
            if (CollectionUtils.isNotEmpty(navEntityList)) {
                NavigationListEntity entity = navEntityList.get(position);
                mTvText.setText(entity.name);
                articles = entity.articles;
                mFlowLayout.setAdapter(new TagAdapter<FeedArticleEntity>(articles) {
                    @Override
                    public View getView(FlowLayout parent, int position, FeedArticleEntity articleEntity) {
                        TextView mTvTitle = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_flow_layout, mFlowLayout, false);
                        if (articleEntity == null) {
                            return null;
                        }
                        mTvTitle.setText(articleEntity.title);
                        mTvTitle.setTextColor(CommonUtils.randomColor());
                        mTvTitle.setPadding(10, 10,
                                10, 10);
                        return mTvTitle;
                    }
                });
            }
        }

        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            FeedArticleEntity entity = articles.get(position);
            SmartPushRoute.startArticleDetailActivity(parent.getContext(), entity.id, entity.title, entity.link, entity.collect, false, false);
            return true;
        }
    }
}
