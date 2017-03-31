package com.vooda.world.view;

import com.vooda.world.bean.TaskBean;

/**
 * Created by leijiaxq
 * Data       2016/12/30 16:00
 * Describe
 */

public interface ICompletedTaskView extends IBaseView{
    void setCompletedTaskList(TaskBean bean);
}
