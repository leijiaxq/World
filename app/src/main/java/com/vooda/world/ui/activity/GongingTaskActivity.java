package com.vooda.world.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;
import com.vooda.world.eventbus.EventObject;
import com.vooda.world.presenter.GongingTaskPresenter;
import com.vooda.world.ui.adapter.GongingTaskAdapter;
import com.vooda.world.ui.widget.pop.TaskHintPop;
import com.vooda.world.utils.ApkUtil;
import com.vooda.world.utils.AppUtil;
import com.vooda.world.utils.DeviceUtil;
import com.vooda.world.utils.LogUtil;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.IGongingTaskView;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zlc.season.rxdownload.RxDownload;


/**
 * Created by leijiaxq
 * Data       2016/12/30 15:03
 * Describe   进行中的任务  --页面
 */

public class GongingTaskActivity extends BaseActivity implements IGongingTaskView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.left_tv)
    TextView           mLeftTv;
    @BindView(R.id.title_tv)
    TextView           mTitleTv;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_reflesh_layout)
    SwipeRefreshLayout mSwipeRefleshLayout;

    private List<TaskBean.UserTaskList> mDatas;
    private GongingTaskAdapter          mAdapter;
    private GongingTaskPresenter        mPresenter;

    private int     mPageIndex   = 1;
    private boolean mIsAllLoaded = false;
    private boolean mRefresh     = true;

    //时间标记
    private String mTimeFlag = "";

    private RxDownload mRxDownload;

    private String packeage = "";

    private boolean mDetectionFlag = false;  //在新开线程中,用于检测应用是否处于前台进程
    private int     mDetectionTime = 0;//在新开线程中,用于检测应用是否处于前台进程的时间标记

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gonging_task;
    }

    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mLeftTv.setVisibility(View.VISIBLE);
        mTitleTv.setText("进行中的任务");
    }

    @Override
    protected void loadData() {
        EventBusUtil.register(this);

        mPresenter = new GongingTaskPresenter(this, this);

        mSwipeRefleshLayout.setOnRefreshListener(this);
        mSwipeRefleshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GongingTaskActivity.this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= Contants.SIZE) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
            }

        });

        initAdapter();

        showProgressDialog("正在加载");
        getData();

        mRxDownload = RxDownload.getInstance()
                .context(this)
                .autoInstall(true)  // 下载完成自动安装
                .maxDownloadNumber(3);//最大下载数量

    }

    //请求网络数据
    private void getData() {
        mPresenter.getGongingTaskList(BaseApplication.getInstance().getUserInfoBean().getUserID(), mPageIndex, 1);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        mIsAllLoaded = false;
        mRefresh = true;
        mSwipeRefleshLayout.setRefreshing(true);
        getData();
    }


    private void loadMore() {
        mPageIndex++;
        getData();
    }


    private void initAdapter() {
        mAdapter = new GongingTaskAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GongingTaskAdapter.OnItemClickListener() {
            @Override
            public void onReceiveReward(int position) {
                //领取任务奖金
                receiveReward(position);
            }
        });

    }


    //打开任务，领取奖励，判断服务器返回的数据中，是否有需要提示的信息，判断用户是否安装应用，如果没有安装应用，就安装，，已经安装了，就打开应该，并调用领取奖励的接口
    private void receiveReward(final int position) {
        LogUtil.d("---receiveReward--");
        TaskBean.UserTaskList bean = mDatas.get(position);

        if (bean.getIsInstall() == 1) {  //通过APK文件安装过应用了

            if (TextUtils.isEmpty(bean.getTaskPrompt())) {   //是否有提示内容，没有就直接打开APP领取奖励
                startApp(position);
            } else {//有的话，需要先弹出pop显示内容

                TaskHintPop taskHintPop = new TaskHintPop(this, bean.getTaskPrompt());
                taskHintPop.setOnPopListenter(new TaskHintPop.OnPopListenter() {
                    @Override
                    public void onConfirm() {

                        //打开APP，领取奖励
                        startApp(position);
                    }

                });
                taskHintPop.showAtLocation(mTitleTv, Gravity.CENTER, 0, 0);
            }
        } else {//没有通过APK文件进行安装
            installApp(bean, false);
        }
    }

    //根据包名启动应用程序,如果没有安装该应用，就打开安装页，进行重新安装
    private void startApp(int position) {
        final TaskBean.UserTaskList bean = mDatas.get(position);

        String packagename = bean.getAppPackeage();
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {   //没有安装该应用

            //普通的安装方法
            installApp(bean, false);
            //            installApp(bean, true);
        } else {

           /* long lastUpdateTime = packageinfo.lastUpdateTime;
            long currentTimeMillis = System.currentTimeMillis();
            //一天的时间
            long dayTimeMillis = (long) 1000 * 60 * 60 * 24 * 1;
            //应用最后更新的时间
            long time = currentTimeMillis - lastUpdateTime;
            if (time > dayTimeMillis) {
                //判断为已安装的应用不对，需要重新安装
                installApp(bean, false);
            } else {*/

            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);

            // 通过getPackageManager()的queryIntentActivities方法遍历
            List<ResolveInfo> resolveinfoList = getPackageManager()
                    .queryIntentActivities(resolveIntent, 0);

            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                // packagename = 参数packname
                String packageName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent
                Intent intent = new Intent(Intent.ACTION_MAIN);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                // 设置ComponentName参数1:packagename参数2:MainActivity路径
                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                startActivity(intent);


                String manufacturer = DeviceUtil.getManufacturer();
                if (!TextUtils.isEmpty(manufacturer) && manufacturer.toLowerCase().endsWith("xiaomi")) {

                    final String packeageName = bean.getAppPackeage();
                    mDetectionFlag = true;
                    mDetectionTime = 0;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (mDetectionFlag && mDetectionTime < 15) {
                                try {
                                    Thread.sleep(2000);
                                    mDetectionTime++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                boolean appForeground = AppUtil.isAppForeground(GongingTaskActivity.this, packeageName);
                                if (appForeground) {
                                    mDetectionFlag = false;
                                    receiveRewardByOpenApp(bean);
                                }
                            }
                        }
                    }).start();

                } else {
                    receiveRewardByOpenApp(bean);

                }


              /*  //tip:已经打开了应用
                //调用修改任务即时状态的方法,领取奖励
                bean.setIsDownload(1);
                bean.setIsOpen(1);

                //   还需要调用修改任务即时状态的接口------成功领取了奖励，把已完成的任务移除
                packeage = bean.getAppPackeage();
                mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());*/

                //用于判断如果前一个是时间标题，后一个也是时间标题或者是null，那么就把时间标题也给移除掉
                //前一个
                TaskBean.UserTaskList beanpre = mDatas.get(position - 1);

                if (mDatas.size() == position + 1 || !TextUtils.isEmpty(mDatas.get(position + 1).getTimeFlag())) {
                    if (!TextUtils.isEmpty(beanpre.getTimeFlag())) {
                        mDatas.remove(beanpre);
                    }
                }
                mDatas.remove(bean);
                mAdapter.notifyDataSetChanged();

               /* //在这里领取了任务奖励，需要通知任务下载页刷新UI
                EventBusUtil.postInfoEvent(Contants.FOUR, new EventMessage(bean.getAppPackeage()));*/

            }
            //        }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mDetectionFlag = false;
    }


    //通过打开APP领取任务奖励
    private void receiveRewardByOpenApp(TaskBean.UserTaskList bean) {
        //tip:已经打开了应用
        //调用修改任务即时状态的方法,领取奖励
        bean.setIsDownload(1);
        bean.setIsOpen(1);

        //                mAdapter.notifyItemChanged(position + 1);
        packeage = bean.getAppPackeage();

        //   还需要调用修改任务即时状态的接口------任务下载完成---isOpen 1, isFinish 1;
        mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());
    }



    //flag   true 能静默安装就静默安装，false 普通的安装方法
    private void installApp(TaskBean.UserTaskList bean, boolean flag) {
        File file = getApkFile(bean);
        if (!file.exists()) {
            ToastUtil.showShort(GongingTaskActivity.this, "文件不存在,请在任务列表重新下载");
            return;
        }

        if (flag) {
            ApkUtil.install(file.getAbsolutePath(), this);
        } else {
            ApkUtil.commonInstall(file.getAbsolutePath(), this);
        }
    }

    //获取APK文件
    private File getApkFile(TaskBean.UserTaskList bean) {
        File file;
        if (bean.getRecord() == null) {
            file = mRxDownload.getRealFiles(bean.getSaveName(), null)[0];
        } else {
            file = mRxDownload.getRealFiles(bean.getRecord().getSaveName(), bean.getRecord().getSavePath())[0];
        }
        return file;
    }


    //服务器返回正在进行中的任务数据
    @Override
    public void setGongingTaskList(TaskBean taskBean) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
        if (taskBean == null || taskBean.getUserTaskList() == null) {
            ToastUtil.showShort(GongingTaskActivity.this, Contants.ERROR);
            return;
        }

        if (taskBean.getUserTaskList().size() < Contants.SIZE) {   //数据加载完了
            mIsAllLoaded = true;
        }

        if (mRefresh || mPageIndex == 1) {
            mRefresh = false;
            mDatas.clear();
            mTimeFlag = "";
        }


        //这里是为了测试写的代码
    /*    List<TaskBean.UserTaskListBean> list = new ArrayList<>();
        for (TaskBean.UserTaskListBean bean8 : taskBean.getUserTaskList()) {
            if (bean8.getIsOpen() != 1) {
                list.add(bean8);
            }
        }
        taskBean.setUserTaskList(list);*/
        //---------------------


        for (int i = 0, length = taskBean.getUserTaskList().size(); i < length; i++) {
            TaskBean.UserTaskList bean = taskBean.getUserTaskList().get(i);

            if (!TextUtils.isEmpty(bean.getReceiveDate())) {
                String[] split = bean.getReceiveDate().split(" ");
                if (split != null && split.length > 0) {
                    if (!mTimeFlag.equals(split[0])) {
                        TaskBean.UserTaskList bean1 = new TaskBean.UserTaskList();
                        bean1.setTimeFlag(split[0]);
                        mTimeFlag = split[0];
                        mDatas.add(bean1);
                        bean.setShowLine(false);
                    }
                    //设置保存的APK文件的名称
                   /* String saveNameByUrl = getSaveNameByUrl(bean.getTaskUrl());
                    bean.setSaveName(saveNameByUrl);*/

                    //设置保存的APK文件的名称
                    String saveNameByTitle = "任务.apk";
                    if (!TextUtils.isEmpty(bean.getTaskTitle())) {
                        saveNameByTitle = bean.getTaskTitle() + ".apk";
                    }
                    bean.setSaveName(saveNameByTitle);

                    mDatas.add(bean);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }


    //截取Url最后一段作为文件保存
    private String getSaveNameByUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }


    //返回
    @OnClick(R.id.left_tv)
    void backClick(View view) {
        finish();
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        mSwipeRefleshLayout.setRefreshing(false);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(this, msg);
        } else {
            ToastUtil.showShort(this, Contants.FAILURE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }


    @Override
    public void setUpdateUserStausResult(int isPay4, int taskID) {

        //在这里领取了任务奖励，需要通知任务下载页刷新UI
        EventBusUtil.postInfoEvent(Contants.FOUR, new EventMessage(packeage + "_" + isPay4));
    }

    @Override
    public void setUpdateStausResultFail(String msg, int taskID) {
        //        EventBusUtil.postInfoEvent(Contants.FOUR, new EventMessage(bean.getAppPackeage()));
    }

    @Subscribe
    public void onEventMainThread(EventObject eventObject) {
        if (eventObject.id == Contants.FIVE) {
            EventMessage message = (EventMessage) eventObject.obj;
            String packageName = message.getMsg();

            for (TaskBean.UserTaskList bean : mDatas) {
                if (packageName.equals(bean.getAppPackeage())) {
                    if (bean.getIsInstall() == 0) {
                        bean.setIsInstall(1);
                        mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());
                    }
                    break;
                }
            }

        }
    }


}
