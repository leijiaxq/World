package com.vooda.world.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.contant.Contants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leijiaxq
 * Data       2016/12/9 10:55
 * Describe
 */
public class PaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context                               mContext;
    private List<PaymentsBean.BalancePayList> mDatas;
    private LayoutInflater                        inflater;


    public PaymentsAdapter(Context context, List<PaymentsBean.BalancePayList> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Contants.TYPE1:
                view = inflater.inflate(R.layout.item_payments_type1, parent, false);
                return new ViewHolderPaymentType1(view);
            case Contants.TYPE2:
                view = inflater.inflate(R.layout.item_payments_type2, parent, false);
                return new ViewHolderPaymentType2(view);
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

        PaymentsBean.BalancePayList dataBean = mDatas.get(position);
        if (TextUtils.isEmpty(dataBean.getDateFlag())) {
            return Contants.TYPE2;
        }
        return Contants.TYPE1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderPaymentType1) {
            setDataType1((ViewHolderPaymentType1) holder, position);
        } else if (holder instanceof ViewHolderPaymentType2) {
            setDataType2((ViewHolderPaymentType2) holder, position);
        }
    }

    //设置type1 的数据
    private void setDataType1(ViewHolderPaymentType1 holder, int position) {
        PaymentsBean.BalancePayList dataBean = mDatas.get(position);
        if (!TextUtils.isEmpty(dataBean.getDateFlag())) {
            holder.mItemType1Tv.setText(dataBean.getDateFlag());
        } else {
            holder.mItemType1Tv.setText("");
        }
    }

    //设置type2 的数据
    private void setDataType2(ViewHolderPaymentType2 holder, int position) {
        PaymentsBean.BalancePayList dataBean = mDatas.get(position);
        holder.mItemTitleTv.setText(TextUtils.isEmpty(dataBean.getBCName()) ? "" : dataBean.getBCName());
        holder.mItemContentTv.setText(TextUtils.isEmpty(dataBean.getChangeDate()) ? "" : dataBean.getChangeDate());

        if ("1".equals(dataBean.getCType())) {  //支出
            holder.mItemAddTv.setVisibility(View.GONE);
            holder.mItemReduceTv.setVisibility(View.VISIBLE);

            holder.mItemReduceTv.setText("-" + dataBean.getCMoney() + "元");

        } else {  //收入
            holder.mItemAddTv.setVisibility(View.VISIBLE);
            holder.mItemReduceTv.setVisibility(View.GONE);

            holder.mItemAddTv.setText("+" + dataBean.getCMoney() + "元");
        }

        if (dataBean.isShowLine()) {       //是否隐藏和显示线条
            holder.mItemPayV.setVisibility(View.VISIBLE);
        } else {
            holder.mItemPayV.setVisibility(View.GONE);
        }


    }

    static class ViewHolderPaymentType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type1_tv)
        TextView mItemType1Tv;

        ViewHolderPaymentType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderPaymentType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title_tv)
        TextView mItemTitleTv;
        @BindView(R.id.item_content_tv)
        TextView mItemContentTv;
        @BindView(R.id.item_add_tv)
        TextView mItemAddTv;
        @BindView(R.id.item_reduce_tv)
        TextView mItemReduceTv;
        @BindView(R.id.item_pay_v)
        View mItemPayV;

        ViewHolderPaymentType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
