package com.vooda.world.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.PaymentsPresenter;
import com.vooda.world.ui.adapter.PaymentsAdapter;
import com.vooda.world.utils.DateUtil;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IPaymentsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by leijiaxq
 * Data       2016/12/29 16:35
 * Describe   收支明细页面
 */

public class PaymentsActivity extends BaseActivity implements IPaymentsView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.left_tv)
    TextView           mLeftTv;
    @BindView(R.id.title_tv)
    TextView           mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_reflesh_layout)
    SwipeRefreshLayout mSwipeRefleshLayout;

    private int     mPageIndex   = 1;
    private boolean mIsAllLoaded = false;
    private boolean mRefresh     = true;
    private PaymentsAdapter                       mAdapter;
    private List<PaymentsBean.BalancePayList> mDatas;
    private PaymentsPresenter                     mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payments;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("收支明细");
    }

    @Override
    protected void loadData() {
        mPresenter = new PaymentsPresenter(this, this);

        mSwipeRefleshLayout.setOnRefreshListener(this);
        mSwipeRefleshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= Contants.SIZE) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
            }

        });

        initAdapter();
        showProgressDialog("正在加载");
        getData();
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mIsAllLoaded = false;
        mRefresh = true;
        mSwipeRefleshLayout.setRefreshing(true);
        getData();
    }


    private void loadMore() {
        mPageIndex++;
        getData();
    }

    //请求服务器数据
    private void getData() {
        mPresenter.getPaymentsDetail(BaseApplication.getInstance().getUserInfoBean().getUserID(), mPageIndex);
    }

    //初始化adapter
    private void initAdapter() {
        mAdapter = new PaymentsAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

    }

    private long mCurrentTime = System.currentTimeMillis();
    private int  mTimeFlag    = -1;  //时间标记，0最近，1， 7天前，2更早


    //得到支付详情数据
    @Override
    public void setPaymentsDetailData(PaymentsBean paymentsBean) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);

        if (paymentsBean == null || paymentsBean.getBalancePayList() == null) {
            ToastUtil.showShort(this, Contants.ERROR);
            return;
        }

        if (paymentsBean.getBalancePayList().size() < Contants.SIZE) {
            mIsAllLoaded = true;
        }

        if (mRefresh || mPageIndex == 1) {  //刷新
            mTimeFlag = -1;
            mRefresh = false;
            mDatas.clear();
        }

        long monthMillis = (long) 1000 * 60 * 60 * 24 * 30;
        long weekmillis = (long) 1000 * 60 * 60 * 24 * 7;

        // 把数据自定义,因为adapter中的多条目类型的数据
        for (int i = 0, length = paymentsBean.getBalancePayList().size(); i < length; i++) {
            PaymentsBean.BalancePayList bean = paymentsBean.getBalancePayList().get(i);

//            bean.setChangeDate("2017-01-19 15:03:02");

            //把时间字符串转为时间戳
            long l = DateUtil.string2Millis(bean.getChangeDate());
            //比较时间戳
            long timeFlag = mCurrentTime - l;

            if (timeFlag < weekmillis) {  //最近
                if (mTimeFlag != 0) {
                    mTimeFlag = 0;
                    PaymentsBean.BalancePayList bean1 = new PaymentsBean.BalancePayList();
                    bean1.setDateFlag("最近");
                    mDatas.add(bean1);
                    bean.setShowLine(false);  //隐藏线条
                }
                mDatas.add(bean);

            } else if (timeFlag < monthMillis) {  //7天前
                if (mTimeFlag != 1) {
                    mTimeFlag = 1;
                    PaymentsBean.BalancePayList bean1 = new PaymentsBean.BalancePayList();
                    bean1.setDateFlag("7天前");
                    mDatas.add(bean1);
                    bean.setShowLine(false);  //隐藏线条
                }
                mDatas.add(bean);

            } else {                            //更早
                if (mTimeFlag != 2) {
                    mTimeFlag = 2;
                    PaymentsBean.BalancePayList bean1 = new PaymentsBean.BalancePayList();
                    bean1.setDateFlag("更早");
                    mDatas.add(bean1);
                    bean.setShowLine(false);  //隐藏线条
                }
                mDatas.add(bean);
            }
        }

        mAdapter.notifyDataSetChanged();


    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
//        ToastUtil.showShort(mContext, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(PaymentsActivity.this, msg);
        } else {
            ToastUtil.showShort(PaymentsActivity.this, Contants.FAILURE);

        }
    }

    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

}
