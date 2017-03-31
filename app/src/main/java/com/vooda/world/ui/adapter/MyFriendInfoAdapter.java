package com.vooda.world.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.bean.FriendInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:54
 * Describe   我的好友任务详情adapter
 */

public class MyFriendInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context                             mContext;
    private List<FriendInfoBean.TaskListEntity> mDatas;

    public MyFriendInfoAdapter(Context context, List<FriendInfoBean.TaskListEntity> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_friend_info, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        setItemOnClickEvent(itemViewHolder);

        return itemViewHolder;


    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        setItemData((ItemViewHolder) holder, position);


    }

    //设置item数据
    private void setItemData(ItemViewHolder holder, int position) {
        FriendInfoBean.TaskListEntity bean = mDatas.get(position);

        holder.mItemTitleTv.setText(TextUtils.isEmpty(bean.getTaskTitle()) ? "" : bean.getTaskTitle());
        holder.mItemContentTv.setText(TextUtils.isEmpty(bean.getReceiveDate()) ? "" : bean.getReceiveDate());
        holder.mItemMoneyTv.setText(TextUtils.isEmpty(bean.getGetBonus()) ? "" : bean.getGetBonus());

        holder.mItemRightLayout.setVisibility(View.VISIBLE);
        try {
            Double aDouble = Double.valueOf(bean.getGetBonus());
            if (aDouble == 0) {
                holder.mItemRightLayout.setVisibility(View.GONE);
            }
        } catch (NumberFormatException e) {

        }

    }

    //条目点击事件
    private void setItemOnClickEvent(final ItemViewHolder itemViewHolder) {
        if (mOnItemClickListener != null) {
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, itemViewHolder.getLayoutPosition());
                    }
                }
            });
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title_tv)
        TextView     mItemTitleTv;
        @BindView(R.id.item_content_tv)
        TextView     mItemContentTv;
        @BindView(R.id.item_money_tv)
        TextView     mItemMoneyTv;
        @BindView(R.id.item_right_layout)
        LinearLayout mItemRightLayout;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
