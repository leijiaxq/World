package com.vooda.world.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leijiaxq
 * Data       2016/12/28 10:35
 * Describe   提现申请结果页面
 */

public class WithdrawalsApplyActivity extends BaseActivity {
    @BindView(R.id.left_tv)
    TextView  mLeftTv;
    @BindView(R.id.title_tv)
    TextView  mTitleTv;
    @BindView(R.id.apply_result_iv)
    ImageView mApplyResultIv;         //提现申请结果的iv
    @BindView(R.id.apply_result_tv)
    TextView  mApplyResultTv;       //提现申请结果的tv
    @BindView(R.id.cash_used_tv)
    TextView  mCashUsedTv;           //提现金额
    @BindView(R.id.cash_surplus_tv)
    TextView  mCashSurplusTv;       //剩余总额
    @BindView(R.id.result_hint_tv)
    TextView  mResultHintTv;        //提现结果的提示内容
    private int mFlag;   //是否有正在提现的记录  1 有

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawals_apply;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("提现申请成功");

        Intent intent = getIntent();
        mCashUsedTv.setText("¥ " + intent.getStringExtra(Contants.CAN_BALANCE));
        mCashSurplusTv.setText("¥ " + intent.getStringExtra(Contants.USER_BALANCE));

        mFlag = intent.getIntExtra(Contants.USER_BALANCE_FLAG, 0);
        intent.putExtra(Contants.USER_BALANCE_FLAG, 0);
    }

    @Override
    protected void loadData() {

    }

    //确定
    @OnClick(R.id.confirm_btn)
    void confirmClick(View view) {
        EventBusUtil.postInfoEvent(Contants.TWO, new EventMessage(""));
        finish();
    }

    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        if (mFlag != 1) {
            EventBusUtil.postInfoEvent(Contants.ONE, new EventMessage(getIntent().getStringExtra(Contants.CAN_BALANCE)));
        }
        finish();
    }

}
