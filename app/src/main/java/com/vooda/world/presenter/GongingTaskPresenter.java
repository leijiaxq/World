package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.IGongingTaskModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.GongingTaskModel;
import com.vooda.world.view.IGongingTaskView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   收支明细
 */

public class GongingTaskPresenter  implements BasePresenter{

    private final static String TAG = GongingTaskPresenter.class.getSimpleName();

    private Context           mContext;
    private IGongingTaskView  mIGongingTaskView;
    private IGongingTaskModel mIGongingTaskModel;

    public GongingTaskPresenter(Context context, IGongingTaskView iGongingTaskView) {
        mContext = context;
        mIGongingTaskView = iGongingTaskView;
        mIGongingTaskModel = new GongingTaskModel();
    }


    //正在进行中的任务列表
    public void getGongingTaskList(int userID,int pageIndex,int Type) {
        mIGongingTaskModel.getGongingTaskList(userID,pageIndex,Type, new OnBaseCallBack<TaskBean>() {
            @Override
            public void onSuccess(TaskBean bean) {
                mIGongingTaskView.setGongingTaskList(bean);
            }

            @Override
            public void onFailed(String msg) {
                mIGongingTaskView.networkFailed(msg);
            }
        });
    }


    //修改任务的即时状态
    public void updateUserStaus(int userID, final int TaskID, final int IsOpen, final int IsDownload, final int isInstall) {
        mIGongingTaskModel.updateUserStaus(userID,TaskID,IsOpen,IsDownload,isInstall, new OnBaseCallBack<ReceivedRewardBean>() {
            @Override
            public void onSuccess(ReceivedRewardBean bean) {
                if (IsOpen == 1 && IsDownload == 1 && isInstall == 1) {
                    mIGongingTaskView.setUpdateUserStausResult(bean.getIsPay4(),TaskID);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (IsOpen == 1 && IsDownload == 1 && isInstall == 1) {
                    mIGongingTaskView.setUpdateStausResultFail(msg,TaskID);
                }
            }
        });
    }

    @Override
    public void destroy() {
        mIGongingTaskModel.destroy();
    }

    public void installAPPforAPK(int userID, int taskID, int isInstall) {
        mIGongingTaskModel.installAPPforAPK(userID,taskID,isInstall, new OnBaseCallBack<BaseRequestBean>() {
            @Override
            public void onSuccess(BaseRequestBean bean) {
//                mITaskHomeView.setOpenRed(bean);
            }

            @Override
            public void onFailed(String msg) {
//                mITaskHomeView.networkFailed(msg);
            }
        });

    }
}
