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
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.RedDetailHeadBean;
import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leijiaxq
 * Data       2016/12/9 10:55
 * Describe   红包详情adapter
 */
public class RedPacketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context                               mContext;
    private List<RedPacketBean.UserRedListEntity> mDatas;
    private LayoutInflater                        inflater;
    private RedDetailHeadBean                     mBean;


    public RedPacketAdapter(Context context, List<RedPacketBean.UserRedListEntity> datas, RedDetailHeadBean bean) {
        this.mContext = context;
        this.mDatas = datas;
        this.mBean = bean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Contants.TYPE1:
                view = inflater.inflate(R.layout.item_red_packet_type1, parent, false);
                return new ViewHolderType1(view);
            case Contants.TYPE2:
                view = inflater.inflate(R.layout.item_red_packet_type2, parent, false);
                return new ViewHolderType2(view);
            default:
                throw new RuntimeException("there is no type that matches the type " +
                        viewType + " + make sure your using types correctly");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 1 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Contants.TYPE1;
        }
        return Contants.TYPE2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderType2) {
            setDataType2((ViewHolderType2) holder, position);
        }
    }

    //设置type1 的数据
    private void setDataType1(ViewHolderType1 holder, int position) {
        if (mBean != null) {
            if (TextUtils.isEmpty(mBean.getMyRedMoney()) || Double.valueOf(mBean.getMyRedMoney()) == 0) {   //没抢到红包
                holder.mItemRedNothingLayout.setVisibility(View.VISIBLE);
                holder.mItemRedLayout.setVisibility(View.GONE);
            } else { //抢到红包
                holder.mItemRedNothingLayout.setVisibility(View.GONE);
                holder.mItemRedLayout.setVisibility(View.VISIBLE);

                holder.mItemMoneyTv.setText(TextUtils.isEmpty(mBean.getMyRedMoney()) ? "" : mBean.getMyRedMoney());
            }

            holder.mItemRedTotalTv.setText("红包总金额：¥" + mBean.getRedMoney() + "，共" + mBean.getRedCount() + "个，剩余" + mBean.getSurplusCount() + "个");

        }

    }

    //设置type2 的数据
    private void setDataType2(ViewHolderType2 holder, int position) {

        RedPacketBean.UserRedListEntity bean = mDatas.get(position - 1);
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


        String data = "";
        if (!TextUtils.isEmpty(bean.getURedDate())) {
            String[] split = bean.getURedDate().split(" ");
            if (split.length > 1) {
                data = split[1];
            } else if (split.length > 0) {
                data = split[0];
            }
        }
        holder.mItemContentTv.setText(data);
        holder.mItemMoneyTv.setText(TextUtils.isEmpty(bean.getURedMoney()) ? "" : bean.getURedMoney() + "元");

        int userID = BaseApplication.getInstance().getUserInfoBean().getUserID();
        if (userID == bean.getUserID()) {  //是自己，显示星星
            holder.mItemXingIv.setVisibility(View.VISIBLE);
        } else {
            holder.mItemXingIv.setVisibility(View.GONE);
        }

        /*if (bean.isLuckiest()) {  //最幸运的人
            holder.mItemHuangguanIv.setVisibility(View.VISIBLE);
            holder.mItemBestTv.setVisibility(View.VISIBLE);
        } else {
            holder.mItemHuangguanIv.setVisibility(View.GONE);
            holder.mItemBestTv.setVisibility(View.GONE);
        }*/
        if (1 == position) {  //第一个人是最幸运的人
            holder.mItemHuangguanIv.setVisibility(View.VISIBLE);
            holder.mItemBestTv.setVisibility(View.VISIBLE);

            holder.mItemXingIv.setVisibility(View.GONE);
        } else {
            holder.mItemHuangguanIv.setVisibility(View.GONE);
            holder.mItemBestTv.setVisibility(View.GONE);
        }

    }

    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_money_tv)
        TextView     mItemMoneyTv;          //抢到红包后显示的红包金额
        @BindView(R.id.item_red_layout)
        LinearLayout mItemRedLayout;              //抢到红包显示的布局
        @BindView(R.id.item_red_nothing_layout)
        LinearLayout mItemRedNothingLayout; //没抢到红包显示的布局
        @BindView(R.id.item_red_total_tv)
        TextView     mItemRedTotalTv;           //红包的总额，个数，剩余个数

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_huangguan_iv)
        ImageView mItemHuangguanIv;        //手气最佳显示的皇冠
        @BindView(R.id.item_xing_iv)
        ImageView mItemXingIv;           //自己抢到红包，显示的星星
        @BindView(R.id.item_title_tv)
        TextView  mItemTitleTv;
        @BindView(R.id.item_content_tv)
        TextView  mItemContentTv;
        @BindView(R.id.item_money_tv)
        TextView  mItemMoneyTv;
        @BindView(R.id.item_best_tv)
        TextView  mItemBestTv;           //手气最佳提示的文字

        ViewHolderType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
