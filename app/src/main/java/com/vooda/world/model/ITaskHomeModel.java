package com.vooda.world.model;

import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.CodeBean;
import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/27 11:38
 * Describe
 */

public interface ITaskHomeModel extends IBaseModel {

    //获取任务列表
    void getTaskList(int userID, OnBaseCallBack<TaskBean> onBaseCallBack);

    //获取任务列表
    void getRobRed(int userID, OnBaseCallBack<RedRobBean> onBaseCallBack);

    //打开红包
    void getOpenReward(int userID, String URedMoney, OnBaseCallBack<RedOpenBean> onBaseCallBack);

    //修改任务的即时状态
    void updateUserStaus(int userID, int taskID, int isOpen, int IsDownload,int isInstall, OnBaseCallBack<ReceivedRewardBean> onBaseCallBack);

    //修改任务已通过下载的APK文件安装
    void installAPPforAPK(int userID, int taskID, int isInstall, OnBaseCallBack<BaseRequestBean> onBaseCallBack);

    //生成带参数的二维码
    void generateCode(int userID, OnBaseCallBack<CodeBean> onBaseCallBack);

    //   本地判断是否已经分享过二维码链接(生成过二维码)(服务器端需要)
    void isCodeExist(int userID, int isInstall, OnBaseCallBack<BaseRequestBean> onBaseCallBack);

    //检测是否有新版本
    void checkNewVersion(OnBaseCallBack<NewVersionBean> onBaseCallBack);
}
