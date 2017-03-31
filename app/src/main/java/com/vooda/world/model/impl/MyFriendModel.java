package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IMyFriendModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/28 12:41
 * Describe
 */

public class MyFriendModel extends SubscriptionModel implements IMyFriendModel {

    //获取好友列表数据
    @Override
    public void getFriendList(int userID, int pageIndex, final OnBaseCallBack<MyFriendBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<MyFriendBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MyFriendBean myFriendBean) {
                if (Contants.OK.equals(myFriendBean.getResult())){
                    onBaseCallBack.onSuccess(myFriendBean);
                }else{
                    onBaseCallBack.onFailed(myFriendBean.getMessage());
                }
            }
        };

        ApiRequest.getFriendList(userID, pageIndex, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
