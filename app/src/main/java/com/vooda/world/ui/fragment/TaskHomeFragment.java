package com.vooda.world.ui.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.vooda.world.R;
import com.vooda.world.api.ApiManager;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.base.BaseFragment;
import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;
import com.vooda.world.eventbus.EventObject;
import com.vooda.world.presenter.TaskHomePresenter;
import com.vooda.world.ui.activity.RedPacketActivity;
import com.vooda.world.ui.adapter.TaskAdapter;
import com.vooda.world.ui.widget.dialog.NewVersionDialog;
import com.vooda.world.ui.widget.pop.InviteFriendsPop2;
import com.vooda.world.ui.widget.pop.RedPacketNothingPop;
import com.vooda.world.ui.widget.pop.RedPacketPop;
import com.vooda.world.ui.widget.pop.SelectSharePop;
import com.vooda.world.ui.widget.pop.TaskHintPop;
import com.vooda.world.utils.ApkUtil;
import com.vooda.world.utils.AppUtil;
import com.vooda.world.utils.DeviceUtil;
import com.vooda.world.utils.LogUtil;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.ITaskHomeView;

import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadRecord;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.vooda.world.base.BaseApplication.api;

/**
 * Created by leijiaxq
 * Data       2016/12/29 16:35
 * Describe   任务
 */
public class TaskHomeFragment extends BaseFragment implements ITaskHomeView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.title_tv)
    TextView       mTitleTv;
    @BindView(R.id.recycleview_task)
    RecyclerView   mRecycleview;
    @BindView(R.id.task_loading_iv)
    ImageView      mTaskLoadingIv;
    @BindView(R.id.task_loading_tv)
    TextView       mTaskLoadingTv;              //任务正在进行中的tv
    @BindView(R.id.task_loading_layout)
    LinearLayout   mTaskLoadingLayout;           //任务正在进行中的layout
    @BindView(R.id.tv_task_total)
    TextView       mTaskTotalTv;                  //开始任务的tv
    @BindView(R.id.begin_task_btn)
    Button         mBeginTaskBtn;              //底部状态栏中开始所有任务的btn
    //    @BindView(R.id.iv_hint)
    //    ImageView      mIvHint;
    //    @BindView(R.id.no_task_layout)
    //    RelativeLayout mNoTaskLayout;
    @BindView(R.id.invite_friend_iv)
    ImageView      mInviteFriendIv;
    @BindView(R.id.main_content)
    LinearLayout   mMainContent;
    @BindView(R.id.task_open_layout)
    RelativeLayout mTaskOpenLayout;           //底部状态栏的layout

    @BindView(R.id.start_task_layout)
    LinearLayout mStartTaskLayout;    //开始任务的layout

    @BindView(R.id.swipe_reflesh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;    //开始任务的layout


    //    private LinearLayoutManager mLinearLayoutManager;

    private List<TaskBean.UserTaskList> mDatas;
    private TaskAdapter                 mAdapter;
    private TaskHomePresenter           mPresenter;
    private View                        mView;    //抢红包的view;
    private RedRobBean                  mRedRobBean;  //抢红包返回的bean对象

    //    String[] array = new String[]{"https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", "http://s1.music.126.net/download/android/CloudMusic_official_3.7.3_153912.apk",
    //            "http://zhstatic.zhihu.com/pkg/store/zhihu/futureve-mobile-update-release-4.10.1(443).apk", "http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.5.4(392).apk"};
    //
    //    String[] array2 = new String[]{"com.tencent.mobileqq", "com.netease.cloudmusic", "com.zhihu.android", "com.zhihu.daily.android"};


    private RxDownload mRxDownload;
    private boolean mDetectionFlag = false;  //在新开线程中,用于检测应用是否处于前台进程
    private int     mDetectionTime = 0;//在新开线程中,用于检测应用是否处于前台进程的时间标记


    @Override
    protected void initVariables() {
        mDatas = new ArrayList<>();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void loadData() {
        EventBusUtil.register(this);

        //  模拟给个默认用户
        //                BaseApplication.getInstance().getUserInfoBean().setUserID(5);

      /*  UserInfoBean bean = new UserInfoBean();
        bean.setUserID(11);
        bean.setHeadUrl("//ss");
        bean.setIsfollow(0);
        bean.setUserName("小强");
        BaseApplication.getInstance().setUserInfoBean(bean);*/

        //        LogUtil.d("HHHH",DeviceUtil.getManufacturer());

        mPresenter = new TaskHomePresenter(mActivity, this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecycleview.setHasFixedSize(true);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecycleview.setItemAnimator(new DefaultItemAnimator());


        initAdapter();

        showProgressDialog("正在加载");
        getData();


        mRxDownload = RxDownload.getInstance()
                .context(mActivity)
                .autoInstall(true)  // 下载完成自动安装
                .maxDownloadNumber(3);//最大下载数量

        //邀请好友图标动起来
        ObjectAnimator obj = ObjectAnimator.ofFloat(mInviteFriendIv, "rotationY", -15, 0, 45, 90, 135, 180, 225, 270, 315, 360, 375);
        obj.setDuration(4000);
        obj.setRepeatCount(ObjectAnimator.INFINITE);
        obj.setRepeatMode(ObjectAnimator.REVERSE);
        obj.start();

        //任务正在进行中的图标旋转起来
       /* Animation operatingAnim = AnimationUtils.loadAnimation(mActivity, R.anim.image_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        mTaskLoadingIv.startAnimation(operatingAnim);*/

        mPresenter.checkNewVersion();

        // 注册广播, 设置只接受下载完成的广播
        mActivity.registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }


    @Override
    protected void lazyLoad() {
        LogUtil.d("TaskHomeFragment ----- lazyLoad---");
        //        getData();

    /*    List<AppUtil.AppInfo> appsInfo = AppUtil.getAppsInfo(mActivity);
        LogUtil.d(appsInfo.toString());*/


    }

    @Override
    public void onRefresh() {
        getData();
    }

    //获取列表数据
    private void getData() {
        //在这里请求网络,获取列表数据
        mPresenter.getTaskList(BaseApplication.getInstance().getUserInfoBean().getUserID());

    }


    public void initAdapter() {
        mAdapter = new TaskAdapter(mActivity, mDatas);
        mRecycleview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onHeadImageClick(View view, int position) {
                //抢红包
                //                ToastUtil.showShort(mActivity,"抢红包");

                //   调用抢红包接口，根据结果来显示不同的pop
                showProgressDialog("抢红包");
                mView = view;
                mView.setClickable(false);
                //抢红包
                mPresenter.getRobRed(BaseApplication.getInstance().getUserInfoBean().getUserID());

            }

            //打开获取任务奖励
            @Override
            public void onOpenReward(int position) {

                receiveReward(position);

            }

            //任务已经被抢光了
            @Override
            public void onTaskNothing(int position) {
                ToastUtil.showShort(mActivity, "该任务已经被抢光了");
            }

            //安装应用
            @Override
            public void onInstallApk(int position) {

                final TaskBean.UserTaskList bean = mDatas.get(position);
                bean.setIsDownload(1);

                mAdapter.notifyItemChanged(position + 1);

                //   还需要调用修改任务即时状态的接口------任务下载完成---isOpen 0, isDownload 1,inInstall 0;
                mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());

                //在这里，能静默安装就静默安装
                installApp(bean, true);
                handleBottomStatusBar();

            }

            //没有任务时，点击重新拉取任务列表
            @Override
            public void onEmptyItemClick(View v, int layoutPosition) {
                showProgressDialog("正在加载");
                getData();
            }

            //完成下载任务，调用修改任务状态，通知服务器这个任务已经完成了
            @Override
            public void onCompletedTaskDown(int position) {
                TaskBean.UserTaskList bean = mDatas.get(position);
                if (bean.getIsDownload() != 1) {
                    bean.setIsDownload(1);
                    mAdapter.notifyItemChanged(position + 1);
                    mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());
                    handleBottomStatusBar();
                }
            }

            //已获得该奖励
            @Override
            public void onCompleteReward(int position) {
                ToastUtil.showShort(mActivity, "您已获得该任务奖励");
            }
        });
    }

    //flag   true 能静默安装就静默安装，false 普通的安装方法
    private void installApp(TaskBean.UserTaskList bean, boolean flag) {
        File file = getApkFile(bean);
        if (!file.exists()) {
            ToastUtil.showShort(mActivity, "文件不存在，重新下载");
            bean.setStart(true);

            RxPermissions.getInstance(mActivity)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (!granted) {
                                throw new RuntimeException("no permission");
                            }
                        }
                    })
                    .compose(mRxDownload.transformService(bean.getTaskUrl(), bean.getSaveName(), null))
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            ToastUtil.showShort(mActivity, "下载开始");
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtil.showShort(mActivity, "下载任务已存在");
                        }
                    });
        } else {

            if (flag) {
                ApkUtil.install(file.getAbsolutePath(), mActivity);
            } else {
                ApkUtil.commonInstall(file.getAbsolutePath(), mActivity);
            }
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

    //打开任务，领取奖励，判断服务器返回的数据中，是否有需要提示的信息，判断用户是否安装应用，如果没有安装应用，就安装，，已经安装了，就打开应该，并调用领取奖励的接口
    private void receiveReward(final int position) {
        LogUtil.d("---receiveReward--");

        TaskBean.UserTaskList bean = mDatas.get(position);
        if (bean.getIsInstall() == 1) {   //已经通过APK文件进行安装过了

            if (TextUtils.isEmpty(bean.getTaskPrompt())) {   //是否有提示内容，没有就直接打开APP领取奖励
                startApp(position);
                handleBottomStatusBar();
            } else {//有的话，需要先弹出pop显示内容

                TaskHintPop taskHintPop = new TaskHintPop(mActivity, bean.getTaskPrompt());
                taskHintPop.setOnPopListenter(new TaskHintPop.OnPopListenter() {
                    @Override
                    public void onConfirm() {

                        //打开APP，领取奖励
                        startApp(position);
                        handleBottomStatusBar();
                    }

                });
                taskHintPop.showAtLocation(mMainContent, Gravity.CENTER, 0, 0);
            }

        } else {//还没有通过APK文件进行安装，需要重新安装

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
            packageinfo = mActivity.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {   //没有安装该应用

            //普通的安装方法
            installApp(bean, false);
            //            installApp(bean, true);
        } else {

          /*  long lastUpdateTime = packageinfo.lastUpdateTime;
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
            List<ResolveInfo> resolveinfoList = mActivity.getPackageManager()
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
                //                startActivityForResult(intent, (position + 1) * 10000);

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

                                boolean appForeground = AppUtil.isAppForeground(mActivity, packeageName);
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


            }
            //            }
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

        //   还需要调用修改任务即时状态的接口------任务下载完成---isOpen 1, isFinish 1;
        mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode >= 10000) {
            int position = requestCode / 10000 - 1;

            TaskBean.UserTaskList bean = mDatas.get(position);

            boolean appForeground = AppUtil.isAppForeground(mActivity, bean.getAppPackeage());

          *//*  //tip:已经打开了应用
            //调用修改任务即时状态的方法,领取奖励
            bean.setIsDownload(1);
            bean.setIsOpen(1);

            //                mAdapter.notifyItemChanged(position + 1);

            //   还需要调用修改任务即时状态的接口------任务下载完成---isOpen 1, isFinish 1;
            mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());*//*
        }

    }*/


    /*    //静默安装apk的方法
    private void installApkSilent(TaskBean.UserTaskListBean bean) {
        File file = getApkFile(bean);
        boolean b = AppUtil.installAppSilent(mActivity, file.getAbsolutePath());
        LogUtil.d("installAppSilent------" + b);
    }

    //普通的安装apk方法
    private void installApkCommon(TaskBean.UserTaskListBean bean) {
        Uri uri = null;
        if (bean.getRecord() == null) {
            uri = Uri.fromFile(mRxDownload.getRealFiles(bean.getSaveName(), null)[0]);
        } else {
            uri = Uri.fromFile(mRxDownload.getRealFiles(bean.getRecord().getSaveName(), bean.getRecord().getSavePath())[0]);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }*/


    //显示抢红包结果
    private void showRedPacketResultPop() {
        if (mRedRobBean != null) {


            if (1 == mRedRobBean.getIsRob() && 0 == mRedRobBean.getIsReceive()) {   //抢到红包并且没有领取红包(抢到红包自动领取了)  这里针对于首次抢到红包,显示提示pop
                RedPacketPop redPacketPop = new RedPacketPop(mActivity);
                redPacketPop.setOnPopListenter(new RedPacketPop.OnPopListenter() {
                    @Override
                    public void onConfirm() {

                        //这里调用打开红包的接口
                       /* 抢到红包自动领取了
                       showProgressDialog("正在领取");
                        mPresenter.getOpenReward(BaseApplication.getInstance().getUserInfoBean().getUserID(), mRedRobBean.getURedMoney());*/


                        Intent intent = new Intent(mActivity, RedPacketActivity.class);
                        //                        intent.putExtra(Contants.RED_PACKET_NOTHING, true);

                        startActivity(intent);
                    }
                });

                redPacketPop.showAtLocation(mTitleTv, Gravity.CENTER, 0, 0);
            } else if (1 == mRedRobBean.getIsRob() && 1 == mRedRobBean.getIsReceive()) {//抢到红包但是已经领取过了

                Intent intent = new Intent(mActivity, RedPacketActivity.class);
                startActivity(intent);

            } else {  //没抢到红包
                RedPacketNothingPop redPacketNothingPop = new RedPacketNothingPop(mActivity);
                redPacketNothingPop.setOnPopListenter(new RedPacketNothingPop.OnPopListenter() {
                    @Override
                    public void onConfirm() {
                        Intent intent = new Intent(mActivity, RedPacketActivity.class);
                        //                        intent.putExtra(Contants.RED_PACKET_NOTHING, true);

                        startActivity(intent);

                    }
                });
                redPacketNothingPop.showAtLocation(mTitleTv, Gravity.CENTER, 0, 0);
            }
        }
    }

    //邀请好友
    @OnClick(R.id.invite_friend_iv)
    void friendClick(View view) {

        //        showSharePop();
        showInviteFriendsPop(false);
    }


    //开始任务
    @OnClick(R.id.begin_task_btn)
    void beginTaskClick(View view) {
        showInviteFriendsPop(true);
    }

    //显示邀请好友的pop,根据flag 来判断是点击了开始任务还是 邀请好友按钮
    private void showInviteFriendsPop(boolean flag) {
        InviteFriendsPop2 inviteFriendsPop2 = new InviteFriendsPop2(mActivity, flag);
        inviteFriendsPop2.setOnPopListenter(new InviteFriendsPop2.OnPopListenter() {
            @Override
            public void onConfirm() {
                //                Toast.makeText(mActivity, "分享给朋友或是朋友圈", Toast.LENGTH_SHORT).show();
                showSharePop();
            }

            @Override
            public void onCancel() {
                //                Toast.makeText(mActivity, "开始所有任务", Toast.LENGTH_SHORT).show();

                //开始所有下载任务
                startAllTask();
            }
        });
        inviteFriendsPop2.showAtLocation(mMainContent, Gravity.CENTER, 0, 0);
    }


    //设置任务列表数据
    @Override
    public void setTaskListData(TaskBean taskBean) {
        hideProgressDialog();
        mSwipeRefreshLayout.setRefreshing(false);
        if (taskBean == null || taskBean.getUserTaskList() == null) {
            ToastUtil.showShort(mActivity, Contants.ERROR);
            return;
        }

        // 模拟给个真实的下载地址
        /*for (int i = 0; i < array.length; i++) {
            TaskBean.UserTaskListBean bean = taskBean.getUserTaskList().get(i);
            bean.setTaskUrl(array[i]);
            bean.setAppPackage(array2[i]);

            if (i == 0 || i == 1) {
                bean.setTaskPrompt("哈哈哈哈哈哈哈哈哈哈哈哈");
            } else {
                bean.setTaskPrompt("");
            }
            bean.setIsFinish(0);
            bean.setIsOpen(0);

            if (i == 0 || i == 1) {
                bean.setIsFinish(1);
            }
            if (i == 0) {
                bean.setIsOpen(1);
            }

            //设置保存的APK文件的名称
            String saveNameByUrl = getSaveNameByUrl(bean.getTaskUrl());
            bean.setSaveName(saveNameByUrl);

            mDatas.add(bean);
        }*/

        if (mDatas != null) {
            mDatas.clear();
        }

        for (TaskBean.UserTaskList bean : taskBean.getUserTaskList()) {


         /*   bean.setIsOpen(0);
            bean.setIsDownload(0);
            bean.setIsPay4(0);
            bean.setIsInstall(0);*/

            //设置保存的APK文件的名称
            String saveNameByTitle = "任务.apk";
            if (!TextUtils.isEmpty(bean.getTaskTitle())) {
                saveNameByTitle = bean.getTaskTitle() + ".apk";
            }
            //            String saveNameByUrl = getSaveNameByUrl(bean.getTaskUrl());
            bean.setSaveName(saveNameByTitle);

            if (bean.getIsOpen() == 1) {
                //如果任务已经完成了，默认状态是已经开始任务了的
                bean.setStart(true);
            }

        }

        mDatas.addAll(taskBean.getUserTaskList());
        mAdapter.notifyDataSetChanged();

        handleBottomStatusBar();
        isShowStart = false;
    }

    //用于是否显示底部状态栏“开始”按钮的标记，，主要用于首次进入这个页面，若是有已完成的任务了，还能开始所有任务
    private boolean isShowStart = false;  //不需要了判断首次进入页面了，现在改为false

    //用于操控底部状态栏的方法
    private void handleBottomStatusBar() {
        if (mDatas.size() <= 0) {
            mTaskOpenLayout.setVisibility(View.GONE);
        } else {
            mTaskOpenLayout.setVisibility(View.VISIBLE);
            //所有任务奖金总额
            double taskTotal = 0;

            //未完成任务的数
            int unfinishedNumber = 0;
            //未完成任务可获得奖金的总额
            double unfinishedTotal = 0;

            //已完成任务奖金总额
            double finishedTotal = 0;

            //用于判断所有任务是否完成，用来显示任务进行中或是已获得多少奖励
            boolean allTaskCompleted = true;

            //用于判断所有任务是否正在下载中
            boolean allTaskDowning = true;

            DecimalFormat format = new DecimalFormat("0.00");

            for (int i = 0, length = mDatas.size(); i < length; i++) {
                TaskBean.UserTaskList bean = mDatas.get(i);
                double taskBonus = Double.valueOf(bean.getTaskBonus());

                taskTotal += taskBonus;
                if (1 == bean.getIsOpen()) {  //是已经领取了奖励
                    finishedTotal += taskBonus;
                } else { //还未完成的任务奖励总额和任务个数
                    unfinishedTotal += taskBonus;
                    unfinishedNumber++;
                }

                if (1 != bean.getIsDownload()) { // 任务还未全部完成
                    allTaskCompleted = false;
                }

                if (!bean.isStart()) {   //还有任务未开始
                    allTaskDowning = false;
                }

            }

            String unfinishedNumberString = Integer.toString(unfinishedNumber);
            String unfinishedTotalString = format.format(unfinishedTotal);

            String finishedTotalString = format.format(finishedTotal);


            if (!allTaskCompleted) { //所有任务还没完成,判断显示开始按钮或任务正在进行中提示语

                if (allTaskDowning) {  //所有任务都开始下载中

                    mStartTaskLayout.setVisibility(View.GONE);
                    mBeginTaskBtn.setVisibility(View.GONE);
                    mTaskLoadingLayout.setVisibility(View.VISIBLE);
                    mTaskLoadingIv.setVisibility(View.GONE);

                    //                    mTaskLoadingIv.setImageResource(R.mipmap.ico_jinxingzhong);

                    String str2 = "任务正在进行中，已获得奖励" + finishedTotalString + "元";
                    SpannableStringBuilder builder2 = new SpannableStringBuilder(str2);

                    int finishedIndex = str2.indexOf(finishedTotalString);

                    //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                    ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));

                    builder2.setSpan(blueSpan2, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTaskLoadingTv.setText(builder2);


                } else {  //还有任务没开始下载，需要显示开始按钮

                    mStartTaskLayout.setVisibility(View.VISIBLE);
                    mBeginTaskBtn.setVisibility(View.VISIBLE);
                    mTaskLoadingLayout.setVisibility(View.GONE);

                    String str = "共" + unfinishedNumber + "个未完成任务 可获得" + unfinishedTotalString + "元";

                    SpannableStringBuilder builder = new SpannableStringBuilder(str);

                    int numberIndex = str.indexOf(unfinishedNumberString);
                    int totalIndex = str.lastIndexOf(unfinishedTotalString);


                    //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                    ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_red));
                    ForegroundColorSpan blueSpan11 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));


                    builder.setSpan(blueSpan, numberIndex, numberIndex + unfinishedNumberString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.setSpan(blueSpan11, totalIndex, totalIndex + unfinishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTaskTotalTv.setText(builder);

                }


            } else {  //所有任务已经完成，用于判断显示 已获得多少奖励还剩多少奖励  或者是 今日任务已完成提示语

                if (unfinishedNumber == 0) {// 所有任务奖励已经领取了

                    mStartTaskLayout.setVisibility(View.GONE);
                    mBeginTaskBtn.setVisibility(View.GONE);
                    mTaskLoadingLayout.setVisibility(View.VISIBLE);

                    //                mTaskLoadingIv.clearAnimation();
                    mTaskLoadingIv.setVisibility(View.GONE);

                    //                    String finishedTotalString = format.format(finishedTotal);
                    String timeString = "10";

                    String str4 = "恭喜您，今日任务已完成，共获得奖励" + finishedTotalString + "元\n明天" + timeString + "点可继续体验";

                    SpannableStringBuilder builder4 = new SpannableStringBuilder(str4);

                    int finishedIndex = str4.indexOf(finishedTotalString);
                    int timeIndex = str4.lastIndexOf(timeString);

                    //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                    ForegroundColorSpan blueSpan4 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));
                    ForegroundColorSpan blueSpan44 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));

                    builder4.setSpan(blueSpan4, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder4.setSpan(blueSpan44, timeIndex, timeIndex + timeString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTaskLoadingTv.setText(builder4);


                } else if (unfinishedNumber <= mDatas.size()) {  //表明有已经完成的任务了

                   /* if (isShowStart) {   //根据这个标记，来判断是否显示“开始”按钮
                        mStartTaskLayout.setVisibility(View.VISIBLE);
                        mBeginTaskBtn.setVisibility(View.VISIBLE);
                        mTaskLoadingLayout.setVisibility(View.GONE);
                    } else {*/
                    mStartTaskLayout.setVisibility(View.GONE);
                    mBeginTaskBtn.setVisibility(View.GONE);
                    mTaskLoadingLayout.setVisibility(View.VISIBLE);


                    //                    String finishedTotalString = format.format(finishedTotal);

                    //                    if (allTaskCompleted) {  //所有任务都已经完成了，显示已获得的奖励和还剩多少奖励未领取

                    //剩下的奖励总额
                    String surplusTotalString = format.format(taskTotal - finishedTotal);

                    //                        mTaskLoadingIv.clearAnimation();
                    mTaskLoadingIv.setVisibility(View.VISIBLE);
                    mTaskLoadingIv.setImageResource(R.drawable.ico_wancheng);


                    String str3 = "已获得奖励" + finishedTotalString + "元，还剩" + surplusTotalString + "元未领取";
                    SpannableStringBuilder builder3 = new SpannableStringBuilder(str3);

                    int finishedIndex = str3.indexOf(finishedTotalString);
                    int surplusIndex = str3.lastIndexOf(surplusTotalString);

                    //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                    ForegroundColorSpan blueSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));
                    ForegroundColorSpan blueSpan33 = new ForegroundColorSpan(getResources().getColor(R.color.text_red));

                    builder3.setSpan(blueSpan3, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder3.setSpan(blueSpan33, surplusIndex, surplusIndex + surplusTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTaskLoadingTv.setText(builder3);

                    //                    }
                }

           /* if (unfinishedNumber == 0) {// 所有任务奖励已经领取了

                mStartTaskLayout.setVisibility(View.GONE);
                mBeginTaskBtn.setVisibility(View.GONE);
                mTaskLoadingLayout.setVisibility(View.VISIBLE);

//                mTaskLoadingIv.clearAnimation();
                mTaskLoadingIv.setVisibility(View.GONE);

                String finishedTotalString = format.format(finishedTotal);
                String timeString = "10";

                String str4 = "恭喜您，今日任务已完成，共获得奖励" + finishedTotalString + "元\n明天" + timeString + "点可继续体验";

                SpannableStringBuilder builder4 = new SpannableStringBuilder(str4);

                int finishedIndex = str4.indexOf(finishedTotalString);
                int timeIndex = str4.lastIndexOf(timeString);

                //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                ForegroundColorSpan blueSpan4 = new ForegroundColorSpan(getResources().getColor(R.color.orange_thme));
                ForegroundColorSpan blueSpan44 = new ForegroundColorSpan(getResources().getColor(R.color.orange_thme));

                builder4.setSpan(blueSpan4, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder4.setSpan(blueSpan44, timeIndex, timeIndex + timeString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTaskLoadingTv.setText(builder4);


            } else if (unfinishedNumber < mDatas.size()) {  //表明有已经完成的任务了
                if (isShowStart) {   //根据这个标记，来判断是否显示“开始”按钮
                    mStartTaskLayout.setVisibility(View.VISIBLE);
                    mBeginTaskBtn.setVisibility(View.VISIBLE);
                    mTaskLoadingLayout.setVisibility(View.GONE);
                } else {
                    mStartTaskLayout.setVisibility(View.GONE);
                    mBeginTaskBtn.setVisibility(View.GONE);
                    mTaskLoadingLayout.setVisibility(View.VISIBLE);


                    String finishedTotalString = format.format(finishedTotal);

                    if (allTaskCompleted) {  //所有任务都已经完成了，显示已获得的奖励和还剩多少奖励未领取

                        //剩下的奖励总额
                        String surplusTotalString = format.format(taskTotal - finishedTotal);

//                        mTaskLoadingIv.clearAnimation();
                        mTaskLoadingIv.setImageResource(R.mipmap.ico_wancheng);


                        String str3 = "已获得奖励" + finishedTotalString + "元，还剩" + surplusTotalString + "元未领取";
                        SpannableStringBuilder builder3 = new SpannableStringBuilder(str3);

                        int finishedIndex = str3.indexOf(finishedTotalString);
                        int surplusIndex = str3.lastIndexOf(surplusTotalString);

                        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                        ForegroundColorSpan blueSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.orange_thme));
                        ForegroundColorSpan blueSpan33 = new ForegroundColorSpan(getResources().getColor(R.color.orange_thme));

                        builder3.setSpan(blueSpan3, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        builder3.setSpan(blueSpan33, surplusIndex, surplusIndex + surplusTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mTaskLoadingTv.setText(builder3);


                    } else {  //任务还在进行中，显示已获得的奖励
//                        mTaskLoadingIv.setImageResource(R.mipmap.ico_jinxingzhong);

                        String str2 = "任务正在进行中，已获得奖励" + finishedTotalString + "元";
                        SpannableStringBuilder builder2 = new SpannableStringBuilder(str2);

                        int finishedIndex = str2.indexOf(finishedTotalString);

                        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                        ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.orange_thme));

                        builder2.setSpan(blueSpan2, finishedIndex, finishedIndex + finishedTotalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mTaskLoadingTv.setText(builder2);
                    }


            }*/
            }

        }

    }

    //抢红包后返回的数据
    @Override
    public void setRobRed(RedRobBean bean) {
        hideProgressDialog();
        mView.setClickable(true);
        if (bean == null) {  //
            ToastUtil.showShort(mActivity, Contants.ERROR);
            return;
        }
        mRedRobBean = bean;
        showRedPacketResultPop();
    }

    //抢红包失败返回的数据
    @Override
    public void setRobRedFail(String msg) {
        hideProgressDialog();
        mView.setClickable(true);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(mActivity, msg);
        }
    }

    //成功打开红包
    @Override
    public void setOpenRed(RedOpenBean bean) {
        hideProgressDialog();

        //        跳转到红包详情页
        Intent intent = new Intent(mActivity, RedPacketActivity.class);
        startActivity(intent);
    }

    //领取任务成功返回
    @Override
    public void setUpdateUserStausResult(int isPay4, int taskID) {
        for (int i = 0, length = mDatas.size(); i < length; i++) {
            TaskBean.UserTaskList bean = mDatas.get(i);
            if (bean.getTaskID() == taskID) {
                bean.setIsPay4(isPay4);
                mAdapter.notifyItemChanged(i + 1);

                if (isPay4 != 1) {
                    ToastUtil.showShort(mActivity, "任务奖励已被抢光");
                }
                break;
            }
        }
    }

    //领取任务奖励失败返回
    @Override
    public void setUpdateStausResultFail(String msg, int TaskID) {

        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(mActivity, msg);
        }
    }

    //检测服务器新版本的信息返回的结果
    @Override
    public void setcheckNewVersionResult(NewVersionBean bean) {
        if (bean == null) {
            return;
        }
        int appVersionCode = AppUtil.getVersionCode(mActivity);

        Double aDouble = Double.valueOf(bean.getVersion());

        if (aDouble > appVersionCode) {
            //有新版本，需要更新

            showNewVersionDialog(bean);

        }


    }

    //展示新应更新信息
    private void showNewVersionDialog(final NewVersionBean bean) {
        NewVersionDialog dialog = new NewVersionDialog(mActivity, bean.getDetail());

        final String url = bean.getAppUrl();
        dialog.setOnPopListenter(new NewVersionDialog.OnPopListenter() {
            @Override
            public void onConfirm() {
                if (BaseApplication.getInstance().getUserInfoBean().getIsfollow() == 1) {
                    DownloadNewVersionApk(url);
                }
            }
        });
        dialog.show();
    }

    // 获取下载新版本时 队列 id
    private long            enqueueId;
    private DownloadManager mDownloadManager;

    //通过URL 下载新版本APK文件
    private void DownloadNewVersionApk(String url) {
        mDownloadManager = (DownloadManager) mActivity.getSystemService(DOWNLOAD_SERVICE);
        // apkDownloadUrl 是 apk 的下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 获取下载队列 id
        enqueueId = mDownloadManager.enqueue(request);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadCompletedId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            // 检查是否是自己的下载队列 id, 有可能是其他应用的
            if (enqueueId != downloadCompletedId) {
                return;
            }
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(enqueueId);
            Cursor c = mDownloadManager.query(query);
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                // 下载失败也会返回这个广播，所以要判断下是否真的下载成功
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                    // 获取下载好的 apk 路径
                    String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    // 提示用户安装
                    promptInstall(Uri.parse("file://" + uriString));
                }
            }
        }
    };

    //安装 新版本应用
    private void promptInstall(Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(promptInstall);
    }


    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtil.showShort(mActivity, Contants.FAILURE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        mTaskLoadingIv.clearAnimation();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
        //注销接收系统下载文件的广播
        mActivity.unregisterReceiver(receiver);

        EventBusUtil.unregister(this);
       /* mInviteFriendIv.clearAnimation();*/
    }


    //截取Url最后一段作为文件保存
    private String getSaveNameByUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    //开始所有任务
    private void startAllTask() {

        for (int i = 0, length = mDatas.size(); i < length; i++) {
            final TaskBean.UserTaskList bean = mDatas.get(i);

            if (bean.getIsOpen() == 1) {  //已经领取奖励了
                continue;
            }

            bean.setStart(true);   //设置已经开始下载了
           /* bean.setFlag(0);*/

            //            RxPermissions rxPermissions = new RxPermissions(mActivity);
            RxPermissions.getInstance(mActivity)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (!granted) {
                                throw new RuntimeException("no permission");
                            }
                        }
                    })
                    .compose(mRxDownload.transformService(bean.getTaskUrl(), bean.getSaveName(), null))
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            //                            Toast.makeText(mActivity, "下载开始", Toast.LENGTH_SHORT).show();
                            ToastUtil.showShort(mActivity, "下载开始");
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            //                            Toast.makeText(mActivity, "下载任务已存在", Toast.LENGTH_SHORT).show();
                            ToastUtil.showShort(mActivity, "下载任务已存在");
                        }
                    });
        }
        //过滤数据
        //        filterData();

        handleBottomStatusBar();
    }


    //   筛选数据,在点击开始任务后，调用这个方法
    private void filterData() {
        RxDownload.getInstance().context(mActivity).getTotalDownloadRecords()
                .map(new Func1<List<DownloadRecord>, List<TaskBean.UserTaskList>>() {
                    @Override
                    public List<TaskBean.UserTaskList> call(List<DownloadRecord> downloadRecords) {
                        for (int i = 0, length = downloadRecords.size(); i < length; i++) {

                            if (mDatas.size() > i) {
                                TaskBean.UserTaskList bean = mDatas.get(i);
                                bean.setRecord(downloadRecords.get(i));
                            }
                        }
                        return mDatas;
                    }
                })
                .subscribe(new Action1<List<TaskBean.UserTaskList>>() {
                    @Override
                    public void call(List<TaskBean.UserTaskList> downloadBeen) {
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    //弹出分享给微信朋友或是微信朋友圈的pop
    private void showSharePop() {

        SelectSharePop selectSharePop = new SelectSharePop(mActivity);
        selectSharePop.setShareListener(new SelectSharePop.ShareListener() {
            @Override
            public void onItem(int position) {
                //                if (position == 1) {
                //                    //分享给朋友
                shareFriend(position);
                //                } else if (position == 2) {
                //                    //分享到朋友圈
                //                    shareFriends();
                //                }
            }
        });
        selectSharePop.showAtLocation(mTitleTv, Gravity.BOTTOM, 0, 0);

        if (mPresenter != null) {
            mPresenter.isCodeExist(BaseApplication.getInstance().getUserInfoBean().getUserID(), 1);
        }
    }


    //分享给朋友
    private void shareFriend(int position) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ApiManager.BASE_URL + "api/invite.jsp?UserID=" + BaseApplication.getInstance().getUserInfoBean().getUserID();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "您的好友“" + BaseApplication.getInstance().getUserInfoBean().getUserName() + "”邀请您一起手机轻松赚钱！";
        msg.description = "只要手指点一点就可以赚钱，快来试试！";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zztxlogo);
        //        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;

        req.scene = position == 1 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        api.sendReq(req);

       /* Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle("您的好友“" + BaseApplication.getInstance().getUserInfoBean().getUserName() + "”邀请您一起手机轻松赚钱！");
        wechat.setText("只要手指点一点就可以赚钱，快来试试！");
        wechat.setImageUrl(ApiManager.BASE_URL + "/api/img/zztxlogo.jpg");

        //        File iconDir =newFileStorage().getIconDir();
        //        File file =newFile(iconDir,shareImagename);
        //        wechat.setImagePath(file.getAbsolutePath());

        wechat.setUrl(ApiManager.BASE_URL + "api/invite.jsp?UserID=" + BaseApplication.getInstance().getUserInfoBean().getUserID());
        //        wechat.setUrl(ApiManager.BASE_URL_IMAGE + "/User/TDCode?UserID=" + BaseApplication.getInstance().getUserInfoBean().getUserID());
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(mActivity, Wechat.NAME);
        if (!weixin.isClientValid()) {//如果没有安装微信客户端，则提醒用户安装
            ToastUtil.showShort(mActivity, "微信未安装,请先安装微信");
        }
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);*/
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //    //分享到朋友圈
    //    private void shareFriends() {
    //        Platform.ShareParams wechatMoments = new Platform.ShareParams();
    //        wechatMoments.setTitle("您的好友“" + BaseApplication.getInstance().getUserInfoBean().getUserName() + "”邀请您一起手机轻松赚钱！");
    //        //        wechatMoments.setText("测试内容");
    //
    //        //        File iconDir4 =new FileStorage().getIconDir();
    //        //        File file4 =newFile(iconDir4,shareImagename);
    //        //        wechatMoments.setImagePath(file4.getAbsolutePath());
    //        wechatMoments.setImageUrl(ApiManager.BASE_URL + "/api/img/zztxlogo.jpg");
    //
    //        wechatMoments.setUrl(ApiManager.BASE_URL + "api/invite.jsp?UserID=" + BaseApplication.getInstance().getUserInfoBean().getUserID());
    //        //        wechatMoments.setUrl(ApiManager.BASE_URL_IMAGE + "/User/TDCode?UserID=" + BaseApplication.getInstance().getUserInfoBean().getUserID());
    //        wechatMoments.setShareType(Platform.SHARE_WEBPAGE);
    //        Platform weixinMoments = ShareSDK.getPlatform(mActivity, WechatMoments.NAME);
    //        if (!weixinMoments.isClientValid()) {
    //            ToastUtil.showShort(mActivity, "微信未安装,请先安装微信");
    //        }
    //        weixinMoments.setPlatformActionListener(this);
    //        weixinMoments.share(wechatMoments);
    //    }


    @Subscribe
    public void onEventMainThread(EventObject eventObject) {
        if (eventObject.id == Contants.FOUR) {                        //在正在进行中的任务页面，领取奖励后，通知任务下载页刷新UI
            EventMessage message = (EventMessage) eventObject.obj;
            String msg = message.getMsg();
            if (!TextUtils.isEmpty(msg)) {
                String[] split = msg.split("_");

                String packageName = "";
                if (split != null && split.length > 1) {
                    packageName = split[0];
                    for (int i = 0, length = mDatas.size(); i < length; i++) {
                        TaskBean.UserTaskList bean = mDatas.get(i);
                        if (packageName.equals(bean.getAppPackeage())) {
                            bean.setIsDownload(1);                            //在这里只需要修改本地数据就可以了，服务器的数据已经在任务正在进行中的页面调用接口修改过了
                            bean.setIsOpen(1);
                            bean.setIsInstall(1);
                            bean.setIsPay4(Integer.valueOf(split[1]));
                            mAdapter.notifyItemChanged(i + 1);
                            handleBottomStatusBar();
                            break;
                        }
                    }
                }
            }
        } else if (eventObject.id == Contants.FIVE) {               //监听到应用安装传递过来的包名
            EventMessage message = (EventMessage) eventObject.obj;
            String packageName = message.getMsg();
            LogUtil.e("onEventMainThread--", packageName);
            for (TaskBean.UserTaskList bean : mDatas) {
                if (packageName.equals(bean.getAppPackeage())) {
                    if (bean.getIsInstall() == 0) {
                        bean.setIsInstall(1);
                        mPresenter.updateUserStaus(BaseApplication.getInstance().getUserInfoBean().getUserID(), bean.getTaskID(), bean.getIsOpen(), bean.getIsDownload(), bean.getIsInstall());
                    }
                    break;
                }
            }
        } else if (eventObject.id == Contants.SIX) {   //点击taskAdapter中item开始下载时，用于修改底部UI
            handleBottomStatusBar();
        }
    }
}
