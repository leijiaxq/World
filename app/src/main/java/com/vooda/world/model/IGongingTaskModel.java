package com.vooda.world.model;

import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/30 15:31
 * Describe
 */

public interface IGongingTaskModel  extends IBaseModel{

    //获取正在进行中的任务
    void getGongingTaskList(int userID, int pageIndex, int Type, OnBaseCallBack<TaskBean> onBaseCallBack);

    //修改任务的即时状态
    void updateUserStaus(int userID, int taskID, int isOpen, int IsDownload,int isInstall, OnBaseCallBack<ReceivedRewardBean> onBaseCallBack);

    //修改任务的即时状态
    void installAPPforAPK(int userID, int taskID, int inStall, OnBaseCallBack<BaseRequestBean> onBaseCallBack);
}
