package com.vooda.world.model;

import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/30 16:00
 * Describe   已完成任务
 */

public interface ICompletedTaskModel extends IBaseModel {

    //获取已完成任务数据
    void getCompletedTaskList(int userID, int pageIndex,int Type, OnBaseCallBack<TaskBean> onBaseCallBack);

}
