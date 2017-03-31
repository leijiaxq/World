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
import com.vooda.world.bean.RedDetailHeadBean;
import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.RedPacketPresenter;
import com.vooda.world.ui.adapter.RedPacketAdapter;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IRedPacketView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by leijiaxq
 * Data       2016/12/28 11:46
 * Describe   红包详情页
 */
public class RedPacketActivity extends BaseActivity implements IRedPacketView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.left_tv)
    TextView           mLeftTv;
    @BindView(R.id.title_tv)
    TextView           mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_reflesh_layout)
    SwipeRefreshLayout mSwipeRefleshLayout;

    private int     mPageIndex   = 0;
    private boolean mIsAllLoaded = false;
    private boolean mRefresh     = true;

    private RedPacketAdapter                    mAdapter;
    private List<RedPacketBean.UserRedListEntity> mDatas;
    private RedPacketPresenter                  mPresenter;

    //红包详情的头布局bean对象
    private RedDetailHeadBean mRedDetailHeadBean;

    //用于判断是从抢到红包页还是未抢到红包页跳转过来的
    private boolean mBooleanExtra = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_packet;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
        mRedDetailHeadBean = new RedDetailHeadBean();

//        Intent intent = getIntent();
//        mBooleanExtra = intent.getBooleanExtra(Contants.RED_PACKET_NOTHING, false);
//        intent.putExtra(Contants.RED_PACKET_NOTHING, false);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("红包详情");
    }

    @Override
    protected void loadData() {
        mPresenter = new RedPacketPresenter(this, this);
        mSwipeRefleshLayout.setOnRefreshListener(this);
        mSwipeRefleshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
      /*  mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });*/

//        initAdapter();
//        showProgressDialog("正在加载");
        getData();
    }

    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
//        mPageIndex = 1;
//        mIsAllLoaded = false;
//        mRefresh = true;
        mSwipeRefleshLayout.setRefreshing(true);
        getData();
    }

  /*  private void loadMore() {
        mPageIndex++;
        getData();
    }*/

    //请求服务器数据
    private void getData() {

        mPresenter.getRedPacketDetail(BaseApplication.getInstance().getUserInfoBean().getUserID()/*, mPageIndex*/);

    }

    //初始化adapter
    private void initAdapter() {
        mAdapter = new RedPacketAdapter(this, mDatas, mRedDetailHeadBean);
        mRecyclerView.setAdapter(mAdapter);

    }

    //设置获取的红包列表数据
    @Override
    public void setRedPacketDetailData(RedPacketBean redPacketBean) {

        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
        if (redPacketBean != null) {


            mRedDetailHeadBean.setMyRedMoney(mBooleanExtra ? "" : redPacketBean.getMyRedMoney());
            mRedDetailHeadBean.setRedCount(redPacketBean.getRedCount());
            mRedDetailHeadBean.setRedMoney(redPacketBean.getRedMoney());
            mRedDetailHeadBean.setSurplusCount(redPacketBean.getSurplusCount());

            if (redPacketBean.getUserRedList() != null) {

                if (mRefresh /*|| mPageIndex == 0*/) {  //刷新
                   /* mRefresh = false;*/
                    mDatas.clear();
                }
                mDatas.addAll(redPacketBean.getUserRedList());

               /* if (redPacketBean.getUserRedList().size() < 10) {
                    mIsAllLoaded = true;
                }


                if (mIsAllLoaded) {   //所有数据加载完成了

                    if ("0".equals(mRedDetailHeadBean.getSurplusCount())) {  //红包个数剩余0个,找出最幸运的用户
                        int position = 0;
                        double maxMoney = 0;

                        for (int i = 0, length = mDatas.size(); i < length; i++) {

                            Double aDouble = Double.valueOf(mDatas.get(i).getURedMoney());
                            if (aDouble >= maxMoney) {
                                maxMoney = aDouble;
                                position = i;
                            }
                        }

                        //设置最幸运的用户
                        mDatas.get(position).setLuckiest(true);
                    }
                }*/

                /*
                //制作假数据
                for (RedPacketBean.UserRedListBean bean : mDatas) {
                    bean.setHeadUrl("http://wx.qlogo.cn/mmopen/tEyibl9iaE4WOnEArDJicCL1nHzL9E0Gzhqkc3yO0EeoQa8W2snD4nNia6I5Kymc1NlLMpZPXubficumPOY8pxHN20yAxcocxH0QD/0");
                }
                mDatas.get(0).setHeadUrl("www.baidu.com");
                mDatas.get(3).setHeadUrl("www.baidu.com");
                mDatas.get(4).setHeadUrl("www.baidu.com");*/

                if (mAdapter == null) {
                    initAdapter();
                }

                mAdapter.notifyDataSetChanged();

            }

        }

    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
//        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(RedPacketActivity.this, msg);
        } else {
            ToastUtil.showShort(RedPacketActivity.this, Contants.FAILURE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
}
