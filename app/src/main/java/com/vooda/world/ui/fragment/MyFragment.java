package com.vooda.world.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.base.BaseFragment;
import com.vooda.world.ui.activity.CompletedTaskActivity;
import com.vooda.world.ui.activity.GongingTaskActivity;
import com.vooda.world.ui.activity.MyFriendActivity;
import com.vooda.world.utils.ImageLoader;
import com.vooda.world.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leijiaxq
 * Data       2016/12/29 16:35
 * Describe   个人中心
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.title_tv)
    TextView  mTvTitle;
    @BindView(R.id.my_icon_iv)
    ImageView mMyIconIv;
    @BindView(R.id.my_name_tv)
    TextView  mMyNameTv;

    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        mTvTitle.setText("我的");
        return view;
    }

    @Override
    protected void loadData() {

        String headUrl = "";
        if (!TextUtils.isEmpty(BaseApplication.getInstance().getUserInfoBean().getHeadUrl())) {
            headUrl = BaseApplication.getInstance().getUserInfoBean().getHeadUrl();
        }
        ImageLoader.getInstance().displayCricleImage(mActivity, headUrl, mMyIconIv);
        mMyNameTv.setText(TextUtils.isEmpty(BaseApplication.getInstance().getUserInfoBean().getUserName()) ? "" : BaseApplication.getInstance().getUserInfoBean().getUserName());
    }

    @Override
    protected void lazyLoad() {
        LogUtil.d("---MyFragment---lazyLoad---");
    }

    //我的好友
    @OnClick(R.id.my_friend_tv)
    void friendClick(View view) {
        Intent intent = new Intent(mActivity, MyFriendActivity.class);
        startActivity(intent);
    }

    //进行中的任务
    @OnClick(R.id.my_gonging_task_tv)
    void gongingClick(View view) {
        Intent intent = new Intent(mActivity, GongingTaskActivity.class);
        startActivity(intent);
    }

    //已完成的任务
    @OnClick(R.id.my_completed_task_tv)
    void completedClick(View view) {
        Intent intent = new Intent(mActivity, CompletedTaskActivity.class);
        startActivity(intent);
    }


}
