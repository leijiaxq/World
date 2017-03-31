package com.vooda.world.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.WithdrawalsPresenter;
import com.vooda.world.ui.widget.pop.SelectMoneyPop;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IWithdrawalsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leijiaxq
 * Data       2016/12/28 9:58
 * Describe   提现页面
 */

public class WithdrawalsActivity extends BaseActivity implements IWithdrawalsView {
    @BindView(R.id.left_tv)
    TextView mLeftTv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.cash_select_tv)
    TextView mCashSelectTv;
    @BindView(R.id.cash_total_tv)
    TextView mCashTotalTv;
    @BindView(R.id.next_btn)
    Button   mNextBtn;


    //提现金额的集合
    private List<String>         mDatas;
    private WithdrawalsPresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("提现");
    }

    @Override
    protected void loadData() {
        String userCanBalance = getIntent().getStringExtra(Contants.USER_CAN_BALANCE);
        mCashTotalTv.setText(TextUtils.isEmpty(userCanBalance) ? "0.00" : userCanBalance);
        mPresenter = new WithdrawalsPresenter(this, this);

        showProgressDialog("正在加载");
        mPresenter.getWebBalance(BaseApplication.getInstance().getUserInfoBean().getUserID());

    }

    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }

    //选择提现金额
    @OnClick(R.id.cash_select_layout)
    void selectClick(View view) {

        if (mDatas == null || mDatas.size() == 0) {
            ToastUtil.showShort(this, Contants.ERROR);
        } else {
            SelectMoneyPop selectMoneyPop = new SelectMoneyPop(this, mDatas);
            selectMoneyPop.setMoneyListener(new SelectMoneyPop.MoneyListener() {
                @Override
                public void onItem(String text) {
                    //选中的提现金额
                    if (!TextUtils.isEmpty(text)) {
                        mCashSelectTv.setText(text + "元");
                    }
                }
            });
            selectMoneyPop.showAtLocation(mNextBtn, Gravity.BOTTOM, 0, 0);
        }

    }

    //下一步
    @OnClick(R.id.next_btn)
    void nextClick(View view) {
        //  这里调用提现接口,提现成功跳转到申请成功页面

        String text = mCashSelectTv.getText().toString();
        if ("请选择".equals(text)) {
            ToastUtil.showShort(this, "请选择提现金额");
            return;
        }
        text = text.replace("元", "");
        String useable = mCashTotalTv.getText().toString();

        if (Double.valueOf(useable) - Double.valueOf(text) < 0) {
            ToastUtil.showShort(this, "可提现金额不足");
            return;
        }
        showProgressDialog("正在提交");
        mNextBtn.setClickable(false);
        mPresenter.getBalanceT(BaseApplication.getInstance().getUserInfoBean().getUserID(), text);

    }

    //设置余额配置数据
    @Override
    public void setWebBalanceData(WithdrawalsBean withdrawalsBean) {
        hideProgressDialog();
        if (withdrawalsBean == null || TextUtils.isEmpty(withdrawalsBean.getValueUrl())) {
            ToastUtil.showShort(this, Contants.ERROR);
            return;
        }
        String[] split = withdrawalsBean.getValueUrl().split("_");
        mDatas.clear();
        for (String str : split) {
            mDatas.add(str);
        }

    }

    //余额提现返回的结果成功
    @Override
    public void setBalanceTResult(WithdrawalsResultBean bean) {
        hideProgressDialog();
        mNextBtn.setClickable(true);

        //跳转到成功提现页面
        Intent intent = new Intent(WithdrawalsActivity.this, WithdrawalsApplyActivity.class);
        intent.putExtra(Contants.CAN_BALANCE, bean.getCanBalance());
        intent.putExtra(Contants.USER_BALANCE, bean.getUserBalance());
        startActivity(intent);
        finish();
    }

    //设置余额提现结果失败
    @Override
    public void setBalanceTResultFail(String msg) {
        hideProgressDialog();
        mNextBtn.setClickable(true);
        //        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(WithdrawalsActivity.this, msg);
        } else {
            ToastUtil.showShort(WithdrawalsActivity.this, Contants.FAILURE);

        }
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        //        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(WithdrawalsActivity.this, msg);
        } else {
            ToastUtil.showShort(WithdrawalsActivity.this, Contants.FAILURE);

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

