package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;
import com.vooda.world.model.IWithdrawalsModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.WithdrawalsModel;
import com.vooda.world.view.IWithdrawalsView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   提现
 */

public class WithdrawalsPresenter  implements BasePresenter{


    private Context        mContext;
    private IWithdrawalsView  mIWithdrawalsView;
    private IWithdrawalsModel mIWithdrawalsModel;

    public WithdrawalsPresenter(Context context, IWithdrawalsView iWithdrawalsView) {
        mContext = context;
        mIWithdrawalsView = iWithdrawalsView;
        mIWithdrawalsModel = new WithdrawalsModel();
    }


    //获取提现金额配置
    public void getWebBalance(int userID) {
        mIWithdrawalsModel.getWebBalance(userID, new OnBaseCallBack<WithdrawalsBean>() {
            @Override
            public void onSuccess(WithdrawalsBean WithdrawalsBean) {
                mIWithdrawalsView.setWebBalanceData(WithdrawalsBean);
            }

            @Override
            public void onFailed(String msg) {
                mIWithdrawalsView.networkFailed(msg);
            }
        });
    }

    //余额提现
    public void getBalanceT(int userID, String amoney) {
        mIWithdrawalsModel.getBalanceT(userID,amoney, new OnBaseCallBack<WithdrawalsResultBean>() {
            @Override
            public void onSuccess(WithdrawalsResultBean bean) {
                mIWithdrawalsView.setBalanceTResult(bean);
            }

            @Override
            public void onFailed(String msg) {
                mIWithdrawalsView.setBalanceTResultFail(msg);
            }
        });

    }

    @Override
    public void destroy() {
        mIWithdrawalsModel.destroy();
    }
}
