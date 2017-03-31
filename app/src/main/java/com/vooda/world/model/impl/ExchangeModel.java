package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IExchangeModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/30 18:48
 * Describe
 */

public class ExchangeModel extends SubscriptionModel implements IExchangeModel {

    @Override
    public void getWalletDetail(int userID, final OnBaseCallBack<ExchangeBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<ExchangeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ExchangeBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };
        ApiRequest.getWalletDetail(userID,subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void isFollowWeixinNumber(int userID, final OnBaseCallBack<FollowBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<FollowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FollowBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };
        ApiRequest.isFollowWeixinNumber(userID,subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }

}
