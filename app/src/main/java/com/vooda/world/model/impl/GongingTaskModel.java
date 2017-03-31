package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.IGongingTaskModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/30 15:32
 * Describe
 */

public class GongingTaskModel extends SubscriptionModel implements IGongingTaskModel {

    //获取正在进行中的任务数据
    @Override
    public void getGongingTaskList(int userID, int pageIndex, int Type, final OnBaseCallBack<TaskBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<TaskBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TaskBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };
        ApiRequest.getGongingTaskList(userID, pageIndex, Type, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void updateUserStaus(int userID, int taskID, int isOpen, int IsDownload,int inStall, final OnBaseCallBack<ReceivedRewardBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<ReceivedRewardBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReceivedRewardBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.updateUserStaus(userID, taskID, isOpen, IsDownload,inStall, subscriber);

        addSubscription(subscriber);
    }

    @Override
    public void installAPPforAPK(int userID, int taskID, int inStall, final OnBaseCallBack<BaseRequestBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<BaseRequestBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseRequestBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };
        ApiRequest.installAPPforAPK(userID, taskID, inStall, subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }

}
