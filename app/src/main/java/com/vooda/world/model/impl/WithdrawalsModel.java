package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IWithdrawalsModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2017/1/3 15:20
 * Describe   提现
 */

public class WithdrawalsModel extends SubscriptionModel implements IWithdrawalsModel {
    @Override
    public void getWebBalance(int userID, final OnBaseCallBack<WithdrawalsBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<WithdrawalsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WithdrawalsBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getWebBalance(userID, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void getBalanceT(int userID, String amoney, final OnBaseCallBack<WithdrawalsResultBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<WithdrawalsResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WithdrawalsResultBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getBalanceT(userID, amoney, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
