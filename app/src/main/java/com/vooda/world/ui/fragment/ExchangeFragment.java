package com.vooda.world.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.base.BaseFragment;
import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;
import com.vooda.world.eventbus.EventObject;
import com.vooda.world.presenter.ExchangePresenter;
import com.vooda.world.ui.activity.PaymentsActivity;
import com.vooda.world.ui.activity.WithdrawalsActivity;
import com.vooda.world.ui.activity.WithdrawalsApplyActivity;
import com.vooda.world.ui.widget.dialog.FollowHintDialog;
import com.vooda.world.utils.SPUtil;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IExchangeView;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leijiaxq
 * Data       2016/12/29 16:35
 * Describe   兑换
 */
public class ExchangeFragment extends BaseFragment implements IExchangeView {
    @BindView(R.id.title_tv)
    TextView mTvTitle;
    @BindView(R.id.right_tv)
    TextView mTvRight;
    //    @BindView(R.id.exchange_iv)
    //    CircleImageView mExchangeIv;
    @BindView(R.id.exchange_total_tv)
    TextView mExchangeTotalTv;   //钱包总额
    @BindView(R.id.exchange_usable_tv)
    TextView mExchangeUsableTv;  //可用余额
    @BindView(R.id.exchange_history_tv)
    TextView mExchangeHistoryTv; //历史收入

    @BindView(R.id.exchange_friend_tv11)
    TextView mExchangeFriendTv11;
    @BindView(R.id.exchange_friend_tv12)
    TextView mExchangeFriendTv12;
    @BindView(R.id.exchange_friend_tv13)
    TextView mExchangeFriendTv13;
    @BindView(R.id.exchange_friend_tv21)
    TextView mExchangeFriendTv21;
    @BindView(R.id.exchange_friend_tv22)
    TextView mExchangeFriendTv22;
    @BindView(R.id.exchange_friend_tv23)
    TextView mExchangeFriendTv23;
    @BindView(R.id.exchange_friend_tv31)
    TextView mExchangeFriendTv31;
    @BindView(R.id.exchange_friend_tv32)
    TextView mExchangeFriendTv32;
    @BindView(R.id.exchange_friend_tv33)
    TextView mExchangeFriendTv33;
    @BindView(R.id.exchange_friend_tv41)
    TextView mExchangeFriendTv41;
    @BindView(R.id.exchange_friend_tv42)
    TextView mExchangeFriendTv42;
    @BindView(R.id.exchange_friend_tv43)
    TextView mExchangeFriendTv43;
    private ExchangeBean      mExchangeBean;
    private ExchangePresenter mPresenter;

    private DecimalFormat mFormat = new DecimalFormat("0.00");

    @Override
    protected void initVariables() {

        EventBusUtil.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        ButterKnife.bind(this, view);
        mTvRight.setVisibility(View.VISIBLE);
        mTvTitle.setText("钱包");
        mTvRight.setText("收支明细");
        return view;
    }

    @Override
    protected void loadData() {

        mPresenter = new ExchangePresenter(mActivity, this);
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getWalletDetail(BaseApplication.getInstance().getUserInfoBean().getUserID());
        //        mPresenter.isFollowWeixinNumber(BaseApplication.getInstance().getUserInfoBean().getUserID());
    }

    //提现至微信钱包
    @OnClick(R.id.excahnge_cash_tv)
    void cashClick(View view) {

        if (mExchangeBean != null) {

            // 模拟数据
            /*mExchangeBean.setIsFinish(0);*/

            if (mExchangeBean.getIsFinish() == 1) {    //正在提现中,直接跳转到上次提现的结果页

                Intent intent = new Intent(mActivity, WithdrawalsApplyActivity.class);
                intent.putExtra(Contants.CAN_BALANCE, mExchangeBean.getAmoney());
                intent.putExtra(Contants.USER_BALANCE, mExchangeBean.getUserBalance());
                intent.putExtra(Contants.USER_BALANCE_FLAG, 1);

                startActivity(intent);

            } else {
                //重新获取用户是否关注微信公众号/存在异步差问题
                mPresenter.isFollowWeixinNumber(BaseApplication.getInstance().getUserInfoBean().getUserID());

                if (BaseApplication.getInstance().getUserInfoBean().getIsfollow() == 1) {  //用户已关注微信公众号
                    //关注了微信公众号，就直接跳转，否则弹出提示
                    jumpWithdrawalsPage();
                } else {
                   /* FollowHintPop followHintPop = new FollowHintPop(mActivity);
                    followHintPop.setOnPopListenter(new FollowHintPop.OnPopListenter() {
                        @Override
                        public void onConfirm() {
                            jumpWithdrawalsPage();

                        }
                    });
                    followHintPop.showAtLocation(mExchangeIv, Gravity.CENTER, 0, 0);*/


                    FollowHintDialog dialog = new FollowHintDialog(mActivity);
                    dialog.setOnPopListenter(new FollowHintDialog.OnPopListenter() {
                        @Override
                        public void onConfirm() {
                            if (BaseApplication.getInstance().getUserInfoBean().getIsfollow() == 1) {
                                jumpWithdrawalsPage();
                            }
                        }
                    });

                    dialog.show();

                }
            }
        }
    }


    //跳转到提现页面
    private void jumpWithdrawalsPage() {
        Intent intent = new Intent(mActivity, WithdrawalsActivity.class);
        if (mExchangeBean != null) {
            intent.putExtra(Contants.USER_CAN_BALANCE, mExchangeBean.getUserCanBalance());
        }
        startActivity(intent);
    }

    //收支明细
    @OnClick(R.id.right_tv)
    void rightClick(View view) {

        Intent intent = new Intent(mActivity, PaymentsActivity.class);
        startActivity(intent);
    }


    @Override
    public void setExchangeDetailData(ExchangeBean bean) {
        hideProgressDialog();
        if (bean == null) {
            ToastUtil.showShort(mActivity, Contants.ERROR);
            return;
        }
        mExchangeBean = bean;

        setPacketData();
    }

    //根据服务器返回的数据,设置用户是否关注微信公众号
    @Override
    public void setFollowWeixinNumber(FollowBean bean) {
        if (bean != null) {
            if (BaseApplication.getInstance().getUserInfoBean().getIsfollow() != bean.getIsFollow()) {
                BaseApplication.getInstance().getUserInfoBean().setIsfollow(bean.getIsFollow());

                SPUtil.put(mActivity, Contants.USER_FOLLOW, bean.getIsFollow());
            }
        }


    }

    //设置钱包数据
    private void setPacketData() {
        if (mExchangeBean != null) {
            mExchangeTotalTv.setText(TextUtils.isEmpty(mExchangeBean.getUserBalance()) ? "0.00" : mExchangeBean.getUserBalance());

            mExchangeUsableTv.setText(TextUtils.isEmpty(mExchangeBean.getUserCanBalance()) ? "0.00" : mExchangeBean.getUserCanBalance());

            mExchangeHistoryTv.setText(TextUtils.isEmpty(mExchangeBean.getUserSumBalance()) ? "0.00" : mExchangeBean.getUserSumBalance());

            List<ExchangeBean.SonListEntity> sonList = mExchangeBean.getSonList();
            if (sonList != null) {
                for (int i = 0, length = sonList.size(); i < length; i++) {
                    ExchangeBean.SonListEntity bean = sonList.get(i);
                    if (bean.getSonsLevel() == 1) {
                        mExchangeFriendTv11.setText(String.valueOf(bean.getSonsCount()));
                        mExchangeFriendTv12.setText(String.valueOf(bean.getSonsTaskCount()));

                        String SonsEarnString = mFormat.format(bean.getSonsEarn());
                        mExchangeFriendTv13.setText(SonsEarnString);

                    } else if (bean.getSonsLevel() == 2) {
                        mExchangeFriendTv21.setText(String.valueOf(bean.getSonsCount()));
                        mExchangeFriendTv22.setText(String.valueOf(bean.getSonsTaskCount()));

                        String SonsEarnString = mFormat.format(bean.getSonsEarn());
                        mExchangeFriendTv23.setText(SonsEarnString);
                    } else if (bean.getSonsLevel() == 3) {
                        mExchangeFriendTv31.setText(String.valueOf(bean.getSonsCount()));
                        mExchangeFriendTv32.setText(String.valueOf(bean.getSonsTaskCount()));

                        String SonsEarnString = mFormat.format(bean.getSonsEarn());
                        mExchangeFriendTv33.setText(SonsEarnString);
                    } else if (bean.getSonsLevel() == 4) {
                        mExchangeFriendTv41.setText(String.valueOf(bean.getSonsCount()));
                        mExchangeFriendTv42.setText(String.valueOf(bean.getSonsTaskCount()));

                        String SonsEarnString = mFormat.format(bean.getSonsEarn());
                        mExchangeFriendTv43.setText(SonsEarnString);
                    }

                }


            }


        }
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(mActivity, msg);
        } else {
            ToastUtil.showShort(mActivity, Contants.FAILURE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }


    @Subscribe
    public void onEventMainThread(EventObject eventObject) {
        if (eventObject.id == Contants.ONE) {  //申请提现成功后，返回提现金额，用于更新界面数据
            EventMessage bean = (EventMessage) eventObject.obj;
            String msg = bean.getMsg();

            if (mExchangeBean != null) {        //用本地的数据
                //提现的金额
                Double aDouble = Double.valueOf(msg);

                //钱包总额
                Double userBalance = Double.valueOf(mExchangeBean.getUserBalance());

                //钱包可用余额
                Double userCanBalance = Double.valueOf(mExchangeBean.getUserCanBalance());

                DecimalFormat format = new DecimalFormat("0.00");

                mExchangeBean.setUserBalance(format.format(userBalance - aDouble));
                mExchangeBean.setUserCanBalance(format.format(userCanBalance - aDouble));

                //表示正在有一笔正在提现的订单
                mExchangeBean.setIsFinish(1);
                mExchangeBean.setAmoney(format.format(aDouble));

                setPacketData();

            } else { //重新请求网络数据
                if (mPresenter != null) {
                    showProgressDialog("正在加载");
                    mPresenter.getWalletDetail(BaseApplication.getInstance().getUserInfoBean().getUserID());
                }
            }
        }
    }

}
