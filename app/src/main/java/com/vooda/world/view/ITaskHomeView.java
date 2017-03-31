package com.vooda.world.view;

import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.TaskBean;

/**
 * Created by leijiaxq
 * Data       2016/12/27 11:38
 * Describe
 */
public interface ITaskHomeView extends IBaseView{
    void setTaskListData(TaskBean taskBean);

    //抢红包--请求网络成功返回的数据
    void setRobRed(RedRobBean bean);

    //抢红包--请求网络失败返回的数据
    void setRobRedFail(String msg);

    //打开红包
    void setOpenRed(RedOpenBean bean);

    //领取任务奖励成功返回
    void setUpdateUserStausResult(int isPay4,int taskID);

    //领取任务奖励失败返回
    void setUpdateStausResultFail(String msg,int taskID);

    //获取服务器新版本的信息
    void setcheckNewVersionResult(NewVersionBean bean);
}
