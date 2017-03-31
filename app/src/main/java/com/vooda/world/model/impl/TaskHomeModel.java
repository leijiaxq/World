package com.vooda.world.model.impl;


import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.CodeBean;
import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.ITaskHomeModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;


/**
 * Created by leijiaxq
 * Data       2016/12/27 11:38
 * Describe
 */


public class TaskHomeModel extends SubscriptionModel implements ITaskHomeModel {


    //获取任务列表数据
    @Override
    public void getTaskList(int userID, final OnBaseCallBack<TaskBean> onBaseCallBack) {
        Subscriber subscriber =  new Subscriber<TaskBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TaskBean taskBean) {
                if (Contants.OK.equals(taskBean.getResult())) {
                    onBaseCallBack.onSuccess(taskBean);
                } else {
                    onBaseCallBack.onFailed(taskBean.getMessage());
                }
            }
        };

        ApiRequest.getTaskList(userID, subscriber);
        addSubscription(subscriber);
    }

    //抢红包
    @Override
    public void getRobRed(int userID, final OnBaseCallBack<RedRobBean> onBaseCallBack) {
        Subscriber subscriber =   new Subscriber<RedRobBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RedRobBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getRobRed(userID, subscriber);
        addSubscription(subscriber);

    }

    //打开红包
    @Override
    public void getOpenReward(int userID, String URedMoney, final OnBaseCallBack<RedOpenBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<RedOpenBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RedOpenBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.getOpenReward(userID, URedMoney, subscriber);
        addSubscription(subscriber);
    }

    //修改任务的即时状态
    @Override
    public void updateUserStaus(int userID, int taskID, int isOpen, int IsDownload, int isInstall, final OnBaseCallBack<ReceivedRewardBean> onBaseCallBack) {
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

        ApiRequest.updateUserStaus(userID, taskID,isOpen,IsDownload,isInstall, subscriber);
        addSubscription(subscriber);
    }


    //修改任务已通过下载的APK文件安装
    @Override
    public void installAPPforAPK(int userID, int taskID, int isInstall, final OnBaseCallBack<BaseRequestBean> onBaseCallBack) {
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

        ApiRequest.installAPPforAPK(userID, taskID,isInstall, subscriber);
        addSubscription(subscriber);
    }

    //生成带参数的二维码
    @Override
    public void generateCode(int userID, final OnBaseCallBack<CodeBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<CodeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CodeBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.generateCode(userID, subscriber);
        addSubscription(subscriber);

    }

    //   本地判断是否已经分享过二维码链接(生成过二维码)(服务器端需要)
    @Override
    public void isCodeExist(int userID, int isInstall, final OnBaseCallBack<BaseRequestBean> onBaseCallBack) {
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

        ApiRequest.isCodeExist(userID,isInstall, subscriber);
        addSubscription(subscriber);
    }

    //检测是否有新版本
    @Override
    public void checkNewVersion(final OnBaseCallBack<NewVersionBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<NewVersionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewVersionBean bean) {
                if (Contants.OK.equals(bean.getResult())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getMessage());
                }
            }
        };

        ApiRequest.checkNewVersion( subscriber);
        addSubscription(subscriber);
    }

    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
