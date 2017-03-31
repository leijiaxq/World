package com.vooda.world.model.impl;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.ICompletedTaskModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/30 16:00
 * Describe
 */

public class CompletedTaskModel extends SubscriptionModel implements ICompletedTaskModel {


    //获取已完成任务数据
    @Override
    public void getCompletedTaskList(int userID, int pageIndex, int Type, final OnBaseCallBack<TaskBean> onBaseCallBack) {

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
        ApiRequest.getCompletedTaskList(userID, pageIndex,Type, subscriber);

        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }


}
