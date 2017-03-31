package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.FriendInfoBean;
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IMyFriendInfoModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/28 20:52
 * Describe
 */

public class MyFriendInfoModel extends SubscriptionModel implements IMyFriendInfoModel {


    //获取好友任务详情列表数据
    @Override
    public void getFriendInfoList(int userID,int FriendID, int pageIndex,final OnBaseCallBack<FriendInfoBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<FriendInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FriendInfoBean myFriendBean) {
                if (Contants.OK.equals(myFriendBean.getResult())) {
                    onBaseCallBack.onSuccess(myFriendBean);
                } else {
                    onBaseCallBack.onFailed(myFriendBean.getMessage());
                }
            }
        };

        ApiRequest.getFriendInfoList(userID,FriendID, pageIndex,subscriber);

        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
