package com.vooda.world.ui.activity;

import android.content.Intent;
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
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.MyFriendPresenter;
import com.vooda.world.ui.adapter.MyFriendAdapter;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IMyFriendView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by leijiaxq
 * Data       2016/12/28 11:46
 * Describe   我的好友
 */

public class MyFriendActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IMyFriendView {
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

    private MyFriendAdapter                   mAdapter;
    private List<MyFriendBean.FriendListEntity> mDatas;
    private MyFriendPresenter                 mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("我的好友");
    }

    @Override
    protected void loadData() {

        mPresenter = new MyFriendPresenter(this, this);
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


    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
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

        mPresenter.getFriendList(BaseApplication.getInstance().getUserInfoBean().getUserID(), mPageIndex);

    }

    //初始化adapter
    private void initAdapter() {
        mAdapter = new MyFriendAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MyFriendBean.FriendListEntity bean = mDatas.get(position);

                if (bean.getIsSuccess() == 1) {
                    //跳转到好友任务详情
                    Intent intent = new Intent(MyFriendActivity.this, FriendInfoActivity.class);
                    intent.putExtra(Contants.FRIEND_BEAN, bean);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort(MyFriendActivity.this, "该好友还未邀请成功");
                }

            }
        });
    }


    //设置返回的好友列表数据
    @Override
    public void setFriendData(MyFriendBean myFriendBean) {
        mSwipeRefleshLayout.setRefreshing(false);
        hideProgressDialog();

        if (myFriendBean.getFriendList() == null) {            //成功请求,,但是返回的数据有误
            ToastUtil.showShort(this, Contants.ERROR);
            return;
        }

        if (myFriendBean.getFriendList().size() < Contants.SIZE) {   //数据加载完了，避免滑动到底部继续加载
            mIsAllLoaded = true;
        }

        if (mRefresh || mPageIndex == 1) {  //刷新
            mRefresh = false;
            mDatas.clear();
//            if (myFriendBean.getFriendList().size() == 0) {   //友好提示没有数据
//                ToastUtil.showShort(this, "没有数据");
//            }

        }
        mDatas.addAll(myFriendBean.getFriendList());
        mAdapter.notifyDataSetChanged();
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        mSwipeRefleshLayout.setRefreshing(false);
        hideProgressDialog();
//        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(MyFriendActivity.this, msg);
        } else {
            ToastUtil.showShort(MyFriendActivity.this, Contants.FAILURE);

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
