package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IRedPacketModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by  leijiaxq
 * Data        2016/12/31 13:10
 * Describe
 */
public class RedPacketModel extends SubscriptionModel implements IRedPacketModel {

    //获取红包列表数据
    @Override
    public void getRedPacketDetail(int userID, /*int pageIndex,*/ final OnBaseCallBack<RedPacketBean> onBaseCallBack) {
        Subscriber subscriber =   new Subscriber<RedPacketBean>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RedPacketBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getRedPacketDetail(userID,/*pageIndex,*/subscriber);
        addSubscription(subscriber);
    }


    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
