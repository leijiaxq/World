package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.CodeBean;
import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.model.ITaskHomeModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.TaskHomeModel;
import com.vooda.world.view.ITaskHomeView;

/**
 * Created by leijiaxq
 * Data       2016/12/27 11:38
 * Describe
 */

public class TaskHomePresenter implements BasePresenter {


    private Context        mContext;
    private ITaskHomeView  mITaskHomeView;
    private ITaskHomeModel mITaskHomeModel;

    public TaskHomePresenter(Context context, ITaskHomeView iTaskHomeView) {
        mContext = context;
        mITaskHomeView = iTaskHomeView;
        mITaskHomeModel = new TaskHomeModel();
    }

    //获取任务列表数据
    public void getTaskList(int userID) {
        mITaskHomeModel.getTaskList(userID, new OnBaseCallBack<TaskBean>() {
            @Override
            public void onSuccess(TaskBean taskBean) {
                mITaskHomeView.setTaskListData(taskBean);
            }

            @Override
            public void onFailed(String msg) {
                mITaskHomeView.networkFailed(msg);
            }
        });
    }

    //抢红包
    public void getRobRed(int userID) {
        mITaskHomeModel.getRobRed(userID, new OnBaseCallBack<RedRobBean>() {
            @Override
            public void onSuccess(RedRobBean bean) {
                mITaskHomeView.setRobRed(bean);
            }

            @Override
            public void onFailed(String msg) {
                mITaskHomeView.setRobRedFail(msg);
            }
        });
    }

    //领取红包
    public void getOpenReward(int userID, String URedMoney) {
        mITaskHomeModel.getOpenReward(userID, URedMoney, new OnBaseCallBack<RedOpenBean>() {
            @Override
            public void onSuccess(RedOpenBean bean) {
                mITaskHomeView.setOpenRed(bean);
            }

            @Override
            public void onFailed(String msg) {
                mITaskHomeView.networkFailed(msg);
            }
        });
    }

    //修改任务的即时状态
    public void updateUserStaus(int userID, final int TaskID, final int IsOpen, final int IsDownload, final int isInstall) {
        mITaskHomeModel.updateUserStaus(userID, TaskID, IsOpen, IsDownload, isInstall, new OnBaseCallBack<ReceivedRewardBean>() {
            @Override
            public void onSuccess(ReceivedRewardBean bean) {
                if (IsOpen == 1 && IsDownload == 1 && isInstall == 1) {
                    mITaskHomeView.setUpdateUserStausResult(bean.getIsPay4(),TaskID);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (IsOpen == 1 && IsDownload == 1 && isInstall == 1) {
                    mITaskHomeView.setUpdateStausResultFail(msg,TaskID);
                }
            }
        });
    }


    //   修改任务已通过下载的APK文件安装
    public void installAPPforAPK(int userID, int TaskID, int isInstall) {
        mITaskHomeModel.installAPPforAPK(userID, TaskID, isInstall, new OnBaseCallBack<BaseRequestBean>() {
            @Override
            public void onSuccess(BaseRequestBean bean) {
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }

    //生成带参数的二维码
    public void generateCode(int userID) {
        mITaskHomeModel.generateCode(userID, new OnBaseCallBack<CodeBean>() {
            @Override
            public void onSuccess(CodeBean bean) {
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }


    //   本地判断是否已经分享过二维码链接(生成过二维码)(服务器端需要)
    public void isCodeExist(int userID, int isInstall) {
        mITaskHomeModel.isCodeExist(userID, isInstall, new OnBaseCallBack<BaseRequestBean>() {
            @Override
            public void onSuccess(BaseRequestBean bean) {
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }



    //   检测是否有新版本
    public void checkNewVersion() {
        mITaskHomeModel.checkNewVersion(new OnBaseCallBack<NewVersionBean>() {
            @Override
            public void onSuccess(NewVersionBean bean) {
                mITaskHomeView.setcheckNewVersionResult(bean);
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }

    @Override
    public void destroy() {
        mITaskHomeModel.destroy();
    }
}
