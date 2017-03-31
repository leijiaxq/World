package com.vooda.world.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.api.ApiManager;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leijiaxq
 * Data       2016/12/9 10:55
 * Describe
 */
public class CompletedTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context                         mContext;
    private List<TaskBean.UserTaskList> mDatas;
    private LayoutInflater                  inflater;


    public CompletedTaskAdapter(Context context, List<TaskBean.UserTaskList> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Contants.TYPE1:
                view = inflater.inflate(R.layout.item_task_type1, parent, false);
                return new ViewHolderType1(view);
            case Contants.TYPE3:
                view = inflater.inflate(R.layout.item_task_type3, parent, false);
                return new ViewHolderType3(view);
            default:
                throw new RuntimeException("there is no type that matches the type " +
                        viewType + " + make sure your using types correctly");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {

        TaskBean.UserTaskList dataBean = mDatas.get(position);
        if (TextUtils.isEmpty(dataBean.getTimeFlag())) {
            return Contants.TYPE3;
        }
        return Contants.TYPE1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderType3) {
            setDataType3((ViewHolderType3) holder, position);
        }
    }

    //设置type1 的数据
    private void setDataType1(ViewHolderType1 holder, int position) {
        TaskBean.UserTaskList bean = mDatas.get(position);
        holder.mItemType1Tv.setText(TextUtils.isEmpty(bean.getTimeFlag()) ? "" : bean.getTimeFlag());

    }

    //设置type3 的数据
    private void setDataType3(ViewHolderType3 holder, final int position) {
        TaskBean.UserTaskList bean = mDatas.get(position);

        String iconUrl = "";
        if (!TextUtils.isEmpty(bean.getTaskIconUrl())) {
            if (!bean.getTaskIconUrl().startsWith("http")) {
                iconUrl = ApiManager.BASE_URL_IMAGE + bean.getTaskIconUrl();
            } else {
                iconUrl = bean.getTaskIconUrl();
            }
        }
        ImageLoader.getInstance().displayImage(mContext, iconUrl, holder.mItemIconIv);
        holder.mItemTitleTv.setText(TextUtils.isEmpty(bean.getTaskTitle()) ? "" : bean.getTaskTitle());
        holder.mItemContentTv.setText(TextUtils.isEmpty(bean.getTaskContent()) ? "" : bean.getTaskContent());
        holder.mItemMoneyTv.setText(TextUtils.isEmpty(bean.getTaskBonus()) ? "" : "+" + bean.getTaskBonus());

        if (bean.isShowLine()) {       //是否隐藏和显示线条
            holder.mItemV.setVisibility(View.VISIBLE);
        } else {
            holder.mItemV.setVisibility(View.GONE);
        }

    }


    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type1_tv)
        TextView mItemType1Tv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType3 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView    mItemIconIv;
        @BindView(R.id.item_title_tv)
        TextView     mItemTitleTv;
        @BindView(R.id.item_content_tv)
        TextView     mItemContentTv;
        @BindView(R.id.item_money_tv)
        TextView     mItemMoneyTv;
        @BindView(R.id.item_money_layout)
        LinearLayout mItemMoneyLayout;
        @BindView(R.id.item_v)
        View         mItemV;

        ViewHolderType3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
