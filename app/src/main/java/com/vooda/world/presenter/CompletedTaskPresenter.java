package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.ICompletedTaskModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.CompletedTaskModel;
import com.vooda.world.view.ICompletedTaskView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   已完成任务
 */

public class CompletedTaskPresenter implements BasePresenter{


    private Context             mContext;
    private ICompletedTaskView  mICompletedTaskView;
    private ICompletedTaskModel mICompletedTaskModel;

    public CompletedTaskPresenter(Context context, ICompletedTaskView iCompletedTaskView) {
        mContext = context;
        mICompletedTaskView = iCompletedTaskView;
        mICompletedTaskModel = new CompletedTaskModel();
    }


    //获取已完成任务列表
    public void getCompletedTaskList(int userID,int pageIndex,int Type) {
        mICompletedTaskModel.getCompletedTaskList(userID,pageIndex,Type, new OnBaseCallBack<TaskBean>() {
            @Override
            public void onSuccess(TaskBean bean) {
                mICompletedTaskView.setCompletedTaskList(bean);
            }

            @Override
            public void onFailed(String msg) {
                mICompletedTaskView.networkFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {
        mICompletedTaskModel.destroy();
    }
}
