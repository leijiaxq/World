package com.vooda.world.view;

import com.vooda.world.bean.TaskBean;

/**
 * Created by leijiaxq
 * Data       2016/12/30 15:31
 * Describe
 */

public interface IGongingTaskView extends IBaseView{

    //返回正在进行中的任务
    void setGongingTaskList(TaskBean bean);

    void setUpdateUserStausResult(int isPay4, int taskID);

    void setUpdateStausResultFail(String msg, int taskID);
}
