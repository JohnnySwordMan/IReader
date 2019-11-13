package com.abyte.wanandroid.ui.choose.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.core.bean.choose.KnowledgeHierarchyEntity;
import com.abyte.wanandroid.ui.choose.activity.KnowledgeHierarchyDetailActivity;
import com.abyte.wanandroid.utils.CollectionUtils;
import com.abyte.wanandroid.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识体系-Adapter
 * Created by geyan on 2018/9/26
 */
public class KnowledgeHierarchyAdapter extends RecyclerView.Adapter<KnowledgeHierarchyAdapter.KnowledgeViewHolder> {

    private Context mContext;
    private List<KnowledgeHierarchyEntity> knowledgeEntityList = new ArrayList<>();

    public KnowledgeHierarchyAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<KnowledgeHierarchyEntity> list) {
        knowledgeEntityList.clear();
        if (CollectionUtils.isNotEmpty(list)) {
            knowledgeEntityList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return knowledgeEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public KnowledgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.choose_page_item, parent, false);
        return new KnowledgeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KnowledgeViewHolder holder, int position) {
        if (CollectionUtils.isNotEmpty(knowledgeEntityList)) {
            holder.onBindData(position);
        }
    }

    class KnowledgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvTitle;
        TextView mTvContent;
        CardView cardView;
        KnowledgeHierarchyEntity knowledgeHierarchyEntity;

        KnowledgeViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.choose_page_card_view);
            mTvTitle = itemView.findViewById(R.id.tv_choose_page_title);
            mTvContent = itemView.findViewById(R.id.tv_choose_page_content);
            cardView.setOnClickListener(this);
        }

        private void onBindData(int position) {
            knowledgeHierarchyEntity = knowledgeEntityList.get(position);
            mTvTitle.setText(knowledgeHierarchyEntity.name);
            mTvTitle.setTextColor(CommonUtils.randomColor());
            List<KnowledgeHierarchyEntity> children = knowledgeHierarchyEntity.children;
            if (CollectionUtils.isNotEmpty(children)) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < children.size(); i++) {
                    sb.append(children.get(i).name).append("  |  ");
                }
                mTvContent.setText(sb.toString());
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.choose_page_card_view:
                    Intent intent = new Intent(mContext, KnowledgeHierarchyDetailActivity.class);
                    intent.putExtra(KnowledgeHierarchyDetailActivity.KEY_DETAIL_DATA, knowledgeHierarchyEntity.toGson());
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
