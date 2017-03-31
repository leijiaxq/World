package com.vooda.world.model.impl;

import com.vooda.world.utils.LogUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by leijiaxq
 * Data       2017/1/10 18:09
 * Describe   用来管理Subscription的Model
 */


public class SubscriptionModel {

    public CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnsubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
}
