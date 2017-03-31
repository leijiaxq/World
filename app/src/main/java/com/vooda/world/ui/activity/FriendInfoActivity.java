package com.vooda.world.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.api.ApiManager;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.FriendInfoBean;
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.MyFriendInfoPresenter;
import com.vooda.world.ui.adapter.MyFriendInfoAdapter;
import com.vooda.world.utils.ImageLoader;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IMyFriendInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by leijiaxq
 * Data       2016/12/28 14:49
 * Describe   好友任务详情页
 */

public class FriendInfoActivity extends BaseActivity implements IMyFriendInfoView {

    @BindView(R.id.left_tv)
    TextView     mLeftTv;
    @BindView(R.id.title_tv)
    TextView     mTitleTv;
    @BindView(R.id.friend_icon_iv)
    ImageView    mFriendIconIv;
    @BindView(R.id.friend_name_tv)
    TextView     mFriendNameTv;
    @BindView(R.id.friend_content_tv)
    TextView     mFriendContentTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycler_view_layout)
    FrameLayout  mRecyclerViewLayout;


    private MyFriendBean.FriendListEntity mFriendBean;   //好友的ID

    private int     mPageIndex   = 1;
    private boolean mIsAllLoaded = false;

    private MyFriendInfoAdapter                 mAdapter;
    private List<FriendInfoBean.TaskListEntity> mDatas;
    private MyFriendInfoPresenter               mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_friend_info;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
        mFriendBean = (MyFriendBean.FriendListEntity) getIntent().getParcelableExtra(Contants.FRIEND_BEAN);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("好友任务详情");

    }

    @Override
    protected void loadData() {
        mPresenter = new MyFriendInfoPresenter(this, this);

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

        if (mFriendBean != null) {

            String headerurl = "";
            if (!TextUtils.isEmpty(mFriendBean.getHeadUrl())) {
                if (!mFriendBean.getHeadUrl().startsWith("http")) {
                    headerurl = ApiManager.BASE_URL + mFriendBean.getHeadUrl();
                } else {
                    headerurl = mFriendBean.getHeadUrl();
                }
            }
            ImageLoader.getInstance().displayCricleImage(mContext, headerurl, mFriendIconIv);

            mFriendNameTv.setText(TextUtils.isEmpty(mFriendBean.getUserName()) ? "" : mFriendBean.getUserName());
            mFriendContentTv.setText("成功邀请于" + (TextUtils.isEmpty(mFriendBean.getIRDate()) ? "" : mFriendBean.getIRDate()));
        }

        initAdapter();

        showProgressDialog("正在加载");
        getData();
    }


    private void loadMore() {
        mPageIndex++;
        getData();
    }

    //请求服务器数据
    private void getData() {

        mPresenter.getFriendInfoList(BaseApplication.getInstance().getUserInfoBean().getUserID(), mFriendBean.getUserID(), mPageIndex);

    }

    //初始化adapter
    private void initAdapter() {
        mAdapter = new MyFriendInfoAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    /*    mAdapter.setOnItemClickListener(new MyFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ToastUtil.showShort(MyFriendActivity.this, position + "被点击了");
                //跳转到好友任务详情
                Intent intent = new Intent(MyFriendActivity.this, FriendInfoActivity.class);
                intent.putExtra("FriendID", mDatas.get(position).getFriendID());
                startActivity(intent);

            }
        });*/
    }


    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }


    //返回好友任务详情列表数据
    @Override
    public void setFriendInfoData(FriendInfoBean bean) {
        hideProgressDialog();
        if (bean == null) {
            ToastUtil.showShort(FriendInfoActivity.this, Contants.ERROR);
            return;
        }
        if (bean.getTaskList().size() < Contants.SIZE) {   //数据加载完了，避免滑动到底部继续加载
            mIsAllLoaded = true;
        }
        mDatas.addAll(bean.getTaskList());
        mAdapter.notifyDataSetChanged();

    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
//        ToastUtil.showShort(mContext, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(FriendInfoActivity.this, msg);
        } else {
            ToastUtil.showShort(FriendInfoActivity.this, Contants.FAILURE);

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
