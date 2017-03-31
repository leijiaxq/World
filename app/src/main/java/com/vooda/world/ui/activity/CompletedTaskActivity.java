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
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.CompletedTaskPresenter;
import com.vooda.world.ui.adapter.CompletedTaskAdapter;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.ICompletedTaskView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by leijiaxq
 * Data       2016/12/30 15:03
 * Describe   进行中的任务  --页面
 */

public class CompletedTaskActivity extends BaseActivity implements ICompletedTaskView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.left_tv)
    TextView           mLeftTv;
    @BindView(R.id.title_tv)
    TextView           mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_reflesh_layout)
    SwipeRefreshLayout mSwipeRefleshLayout;

    private List<TaskBean.UserTaskList> mDatas;
    private CompletedTaskAdapter        mAdapter;
    private CompletedTaskPresenter      mPresenter;

    private int     mPageIndex   = 1;
    private boolean mIsAllLoaded = false;
    private boolean mRefresh     = true;

    //时间标记
    private String mTimeFlag = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gonging_task;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("已完成任务");
    }

    @Override
    protected void loadData() {
        mPresenter = new CompletedTaskPresenter(this, this);

        mSwipeRefleshLayout.setOnRefreshListener(this);
        mSwipeRefleshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CompletedTaskActivity.this,
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

    //请求网络数据
    private void getData() {
        synchronized (CompletedTaskActivity.class) {
            mPresenter.getCompletedTaskList(BaseApplication.getInstance().getUserInfoBean().getUserID(), mPageIndex, 2);
        }
    }

    @Override
    public void onRefresh() {

        mRefresh = true;
        mPageIndex = 1;
        mIsAllLoaded = false;
        mSwipeRefleshLayout.setRefreshing(true);
        getData();

/*
        BaseApplication.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefresh = true;
                mPageIndex = 1;
                mIsAllLoaded = false;
//                mSwipeRefleshLayout.setRefreshing(true);
                getData();
            }
        }, Contants.REFRESH_TIME);*/

    }


    private void loadMore() {
        mPageIndex++;
        getData();
    }


    private void initAdapter() {
        mAdapter = new CompletedTaskAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    //服务器返回已完成任务列表数据
    @Override
    public void setCompletedTaskList(TaskBean taskBean) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
        if (taskBean == null || taskBean.getUserTaskList() == null) {
            ToastUtil.showShort(CompletedTaskActivity.this, Contants.ERROR);
            return;
        }

        if (taskBean.getUserTaskList().size() < Contants.SIZE) {   //数据加载完了
            mIsAllLoaded = true;
        }

        if (mRefresh == true || mPageIndex == 1) {
            mRefresh = false;
            mDatas.clear();
            mTimeFlag = "";
        }

        //这里是为了测试写的代码
       /* List<TaskBean.UserTaskListBean> list = new ArrayList<>();
        for (TaskBean.UserTaskListBean bean8 : taskBean.getUserTaskList()) {
            if (bean8.getIsOpen() == 1) {
                list.add(bean8);
            }
        }
        taskBean.setUserTaskList(list);*/
        //---------------------


        for (int i = 0, length = taskBean.getUserTaskList().size(); i < length; i++) {
            TaskBean.UserTaskList bean = taskBean.getUserTaskList().get(i);

            if (!TextUtils.isEmpty(bean.getReceiveDate())) {
                String[] split = bean.getReceiveDate().split(" ");
                if (split != null && split.length > 0) {
                    if (!mTimeFlag.equals(split[0])) {
                        TaskBean.UserTaskList bean1 = new TaskBean.UserTaskList();
                        bean1.setTimeFlag(split[0]);
                        mTimeFlag = split[0];
                        mDatas.add(bean1);
                        bean.setShowLine(false);
                    }
                    //设置保存的APK文件的名称
                    //                    String saveNameByUrl = getSaveNameByUrl(bean.getTaskUrl());
                    //                    bean.setSaveName(saveNameByUrl);

                    mDatas.add(bean);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
        //        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(CompletedTaskActivity.this, msg);
        } else {
            ToastUtil.showShort(CompletedTaskActivity.this, Contants.FAILURE);

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
