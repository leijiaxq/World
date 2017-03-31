package com.vooda.world.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.api.ApiManager;
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:54
 * Describe   我的好友adapter
 */

public class MyFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context                           mContext;
    private List<MyFriendBean.FriendListEntity> mDatas;

    public MyFriendAdapter(Context context, List<MyFriendBean.FriendListEntity> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_friend, parent, false);
        MyFriendAdapter.ItemViewHolder itemViewHolder = new MyFriendAdapter.ItemViewHolder(v);
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
        MyFriendBean.FriendListEntity bean = mDatas.get(position);
        String headerurl = "";
        if (!TextUtils.isEmpty(bean.getHeadUrl())) {
            if (!bean.getHeadUrl().startsWith("http")) {
                headerurl = ApiManager.BASE_URL + bean.getHeadUrl();
            } else {
                headerurl = bean.getHeadUrl();
            }
        }
        ImageLoader.getInstance().displayCricleImage(mContext, headerurl, holder.mItemIconIv);

        holder.mItemTitleTv.setText(TextUtils.isEmpty(bean.getUserName()) ? "" : bean.getUserName());

        if ("1".equals(bean.getIsSuccess())) {  //成功邀请
            holder.mItemContentTv.setText("成功邀请于" + (TextUtils.isEmpty(bean.getIRDate()) ? "" : bean.getIRDate()));
        } else { //邀请还未成功
            holder.mItemContentTv.setText("邀请于" + (TextUtils.isEmpty(bean.getIRDate()) ? "" : bean.getIRDate()));
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
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_title_tv)
        TextView  mItemTitleTv;
        @BindView(R.id.item_content_tv)
        TextView  mItemContentTv;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
