package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IPaymentsModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/29 17:26
 * Describe
 */

public class PaymentsModel extends SubscriptionModel implements IPaymentsModel {

    //收支详情数据
    @Override
    public void getPaymentsDetail(int userID, int pageIndex, final OnBaseCallBack<PaymentsBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<PaymentsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PaymentsBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getPaymentsDetail(userID, pageIndex, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
