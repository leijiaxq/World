package com.vooda.world.ui.adapter;

import android.Manifest;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.vooda.world.R;
import com.vooda.world.api.ApiManager;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.eventbus.EventBusUtil;
import com.vooda.world.eventbus.EventMessage;
import com.vooda.world.manager.DownloadController;
import com.vooda.world.utils.ImageLoader;
import com.vooda.world.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadEvent;
import zlc.season.rxdownload.entity.DownloadFlag;
import zlc.season.rxdownload.entity.DownloadStatus;
import zlc.season.rxdownload.function.Utils;

/**
 * Created by leijiaxq
 * Data       2016/12/29 16:35
 * Describe
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context                     mContext;
    private List<TaskBean.UserTaskList> mDatas;


    public TaskAdapter(Context context, List<TaskBean.UserTaskList> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Contants.TYPE1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_head_iv, parent, false);
                return new ImageViewViewHolder(view);

            case Contants.TYPE2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
                ItemViewHolder itemViewHolder = new ItemViewHolder(view, parent);
               /* setItemOnClickEvent(itemViewHolder);*/
                return itemViewHolder;
            case Contants.TYPE3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_empty, parent, false);
                EmptyViewHolder emptyViewHolder = new EmptyViewHolder(view);
                setItemOnClickEvent(emptyViewHolder);
                return emptyViewHolder;

            default:
                throw new RuntimeException("there is no type that matches the type " +
                        viewType + " + make sure your using types correctly");
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            setData((ItemViewHolder) holder, position);
        } else if (holder instanceof ImageViewViewHolder) {
            setImageViewData((ImageViewViewHolder) holder, position);
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Contants.TYPE1;
        } else if (mDatas.size() == 0 && position == 1) {
            return Contants.TYPE3;
        }
        return Contants.TYPE2;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 2;
        } else if (mDatas.size() == 0) {
            return 2;
        } else {
            return mDatas.size() + 1;
        }
    }

    private void setImageViewData(final ImageViewViewHolder holder, final int position) {
        holder.mItemTaskHeadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onHeadImageClick(holder.mItemTaskHeadIv, position);
                }
            }
        });
    }

    private void setData(final ItemViewHolder holder, final int position) {
        TaskBean.UserTaskList bean = mDatas.get(position - 1);

        //给holder设置数据,下载需要
        holder.setItemBeanData(bean);

        String iconUrl = "";
        if (!TextUtils.isEmpty(bean.getTaskIconUrl())) {
            if (!bean.getTaskIconUrl().startsWith("http")) {
                iconUrl = ApiManager.BASE_URL_IMAGE + bean.getTaskIconUrl();
            } else {
                iconUrl = bean.getTaskIconUrl();
            }
        }
        ImageLoader.getInstance().displayImage(mContext, iconUrl, holder.mItemIconIv);
        holder.mItemTitleTv.setText(TextUtils.isEmpty(bean.getTaskTitle()) ? "" : bean.getTaskTitle());
        holder.mItemContentTv.setText(TextUtils.isEmpty(bean.getTaskContent()) ? "" : bean.getTaskContent());
        holder.mItemAmountTv.setText(TextUtils.isEmpty(bean.getTaskBonus()) ? "" : "奖励" + bean.getTaskBonus() + "元");

        //使最后一条不被遮住
        if (position == mDatas.size()) {
            holder.mItemV.setVisibility(View.VISIBLE);
        } else {
            holder.mItemV.setVisibility(View.GONE);
        }


        holder.mItemNothingTv.setVisibility(View.GONE);
        if (1 == bean.getIsDownload()) {   //任务完成
            holder.mItemAmountTv.setVisibility(View.GONE);
            holder.mItemPbLayout.setVisibility(View.GONE);
            holder.mItemRewardTv.setVisibility(View.GONE);
            if (1 == bean.getIsOpen() && 1 == bean.getIsInstall()) {  //已经领取任务奖励

                if (bean.getIsPay4() == 1) {  //成功领取奖励

                    holder.mItemCompleteRewardLayout.setVisibility(View.VISIBLE);
                    holder.mItemCompleteRewardTv.setText("已获得 ¥" + bean.getTaskBonus());
                    //                holder.mItemOpenRewardTv.setBackgroundResource(R.drawable.shape_task_bg_green);
                    holder.mItemOpenRewardTv.setClickable(false);

                    holder.mItemCompleteRewardLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onCompleteReward(position - 1);
                            }
                        }
                    });

                } else { //奖励已经被抢光了
                    holder.mItemCompleteRewardLayout.setVisibility(View.GONE);
                    holder.mItemNothingTv.setVisibility(View.VISIBLE);
                    holder.mItemNothingTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener !=null) {
                                mOnItemClickListener.onTaskNothing(position -1);
                            }
                        }
                    });
                }


            } else { //任务完成,但是还没有领取奖励
                holder.mItemCompleteRewardLayout.setVisibility(View.GONE);
                holder.mItemOpenRewardTv.setVisibility(View.VISIBLE);
                holder.mItemRewardIv.setVisibility(View.VISIBLE);
                //                holder.mItemOpenRewardTv.setText("打开得奖励");
                //                holder.mItemOpenRewardTv.setBackgroundResource(R.drawable.shape_task_bg_blue);
                holder.mItemOpenRewardTv.setClickable(true);
                holder.mItemOpenRewardTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开得奖励
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onOpenReward(position - 1);
                        }
                    }
                });
            }
        } else {  //任务未完成

            holder.mItemAmountTv.setVisibility(View.VISIBLE);
            holder.mItemPbLayout.setVisibility(View.GONE);
            holder.mItemRewardTv.setVisibility(View.GONE);
            holder.mItemOpenRewardTv.setVisibility(View.GONE);

            holder.mItemRewardIv.setVisibility(View.GONE);

            holder.mItemCompleteRewardLayout.setVisibility(View.GONE);
        }

        if (bean.getTaskSurplusCount() == 0 && bean.getIsPay4() == 0) {  //剩余任务个数为0,并且该用户没有完成这个任务,就显示该任务已经被抢光了
            holder.mItemNothingTv.setVisibility(View.VISIBLE);
            holder.mItemNothingTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener !=null) {
                        mOnItemClickListener.onTaskNothing(position -1);
                    }
                }
            });
        }



       /* holder.mItemNothingTv.setVisibility(View.GONE);
        if (1 == bean.getIsDownload()) {   //任务完成
            holder.mItemAmountTv.setVisibility(View.GONE);
            holder.mItemPbLayout.setVisibility(View.GONE);
            holder.mItemRewardTv.setVisibility(View.GONE);
            if (1 == bean.getIsOpen()) {  //已经领取任务奖励
                holder.mItemCompleteRewardLayout.setVisibility(View.VISIBLE);
                holder.mItemCompleteRewardTv.setText("已获得 ¥" + bean.getTaskBonus());
//                holder.mItemOpenRewardTv.setBackgroundResource(R.drawable.shape_task_bg_green);
                holder.mItemOpenRewardTv.setClickable(false);

                holder.mItemCompleteRewardLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onCompleteReward(position - 1);
                        }
                    }
                });

            } else { //任务完成,但是还没有领取奖励
                holder.mItemCompleteRewardLayout.setVisibility(View.GONE);
                holder.mItemOpenRewardTv.setVisibility(View.VISIBLE);
                holder.mItemRewardIv.setVisibility(View.VISIBLE);
//                holder.mItemOpenRewardTv.setText("打开得奖励");
//                holder.mItemOpenRewardTv.setBackgroundResource(R.drawable.shape_task_bg_blue);
                holder.mItemOpenRewardTv.setClickable(true);
                holder.mItemOpenRewardTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开得奖励
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onOpenReward(position - 1);
                        }
                    }
                });
            }
        } else {  //任务未完成

            holder.mItemAmountTv.setVisibility(View.VISIBLE);
            holder.mItemPbLayout.setVisibility(View.GONE);
            holder.mItemRewardTv.setVisibility(View.GONE);
            holder.mItemOpenRewardTv.setVisibility(View.GONE);

            holder.mItemRewardIv.setVisibility(View.GONE);

            holder.mItemCompleteRewardLayout.setVisibility(View.GONE);*/


            /*
                      if (bean.isStart()) {  //任务已经开始
                holder.mItemAmountTv.setVisibility(View.GONE);

                if (1 == bean.getFlag()) {  // 任务正在下载,显示进度条
                    holder.mItemRewardTv.setVisibility(View.INVISIBLE);
                    holder.mItemPbLayout.setVisibility(View.VISIBLE);

                    holder.mItemRewardTv.setClickable(true);
                    holder.mItemRewardTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //任务正在下载
                            downLoadEvent(holder);
                        }
                    });


                } else if (2 == bean.getFlag()) {//任务等待下载
                    holder.mItemRewardTv.setVisibility(View.VISIBLE);
                    holder.mItemPbLayout.setVisibility(View.GONE);
                    holder.mItemRewardTv.setBackgroundResource(R.drawable.shape_task_bg_orange);
                    holder.mItemRewardTv.setText("等待下载中");

                    holder.mItemRewardTv.setClickable(true);
                    holder.mItemRewardTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //任务正在下载
                            downLoadEvent(holder);
                        }
                    });

                } else if (3 == bean.getFlag()) {//任务已经被抢光
                    holder.mItemNothingTv.setVisibility(View.VISIBLE);
                    holder.mItemRewardTv.setVisibility(View.GONE);
                    holder.mItemPbLayout.setVisibility(View.GONE);

                    holder.mItemRewardTv.setClickable(true);
                    holder.mItemRewardTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //任务已经被抢光了
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onTaskNothing(position);
                            }
                        }
                    });

                }


            } else { //任务还没开始,隐藏进度条

                holder.mItemAmountTv.setVisibility(View.VISIBLE);
                holder.mItemPbLayout.setVisibility(View.GONE);
                holder.mItemRewardTv.setVisibility(View.GONE);

                holder.mItemAmountTv.setText("奖励" + bean.getTaskBonus() + "元");

            }

        }*/


        /**
         * important!! 如果有订阅没有取消,则取消订阅!防止ViewHolder复用导致界面显示的BUG!
         */
        Utils.unSubscribe(holder.mSubscription);
        holder.mSubscription = holder.mRxDownload.receiveDownloadStatus(bean.getTaskUrl())
                .subscribe(new Action1<DownloadEvent>() {
                    @Override
                    public void call(DownloadEvent event) {
                        if (event.getFlag() == DownloadFlag.FAILED) {
                            Throwable throwable = event.getError();
                            Log.w("Error", throwable);
                        }
                        if (event.getFlag() == DownloadFlag.COMPLETED) {
                            LogUtil.d("任务下载完成--------" + (position - 1));

                            //完成下载任务，调用修改任务状态，通知服务器这个任务已经完成了
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onCompletedTaskDown(position - 1);
                            }
                        }

                        holder.mDownloadController.setEvent(event);
                        holder.updateProgressStatus(event.getDownloadStatus());
                    }
                });


    }


  /*  private void downLoadEvent(final ItemViewHolder holder) {

        holder.mDownloadController.handleClick(new DownloadController.Callback() {
            @Override
            public void startDownload() {
                holder.start();
            }

            @Override
            public void pauseDownload() {
                holder.pause();
            }

            @Override
            public void cancelDownload() {
                holder.cancel();
            }

            @Override
            public void install() {
                holder.installApk();
            }
        });
    }*/


    //条目点击事件,没有任务时，点击item重新获取数据
    private void setItemOnClickEvent(final EmptyViewHolder emptyViewHolder) {
        if (mOnItemClickListener != null) {
            emptyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onEmptyItemClick(v, emptyViewHolder.getLayoutPosition());
                    }
                }
            });
        }
    }

    public static OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onHeadImageClick(View view, int position);

        //打开奖励
        void onOpenReward(int position);

        //任务被抢光了
        void onTaskNothing(int position);

        //安装应用
        void onInstallApk(int position);

        //没有任务时，点击重新拉取任务列表
        void onEmptyItemClick(View v, int layoutPosition);

        //完成下载任务，调用修改任务状态，通知服务器这个任务已经完成了
        void onCompletedTaskDown(int position);

        //已获得该奖励
        void onCompleteReward(int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView      mItemIconIv;
        @BindView(R.id.item_title_tv)
        TextView       mItemTitleTv;
        @BindView(R.id.item_content_iv)
        ImageView      mItemContentIv;
        @BindView(R.id.item_content_tv)
        TextView       mItemContentTv;
        @BindView(R.id.item_amount_tv)
        TextView       mItemAmountTv;     //任务未完成显示的金额
        @BindView(R.id.item_reward_tv)
        TextView       mItemRewardTv;     //点击下载
        @BindView(R.id.item_open_reward_tv)
        TextView       mItemOpenRewardTv; //任务完成奖励金额
        @BindView(R.id.item_reward_iv)
        ImageView      mItemRewardIv;    //任务完成,但是还没有领取奖励的图标
        @BindView(R.id.item_nothing_tv)
        TextView       mItemNothingTv;    //任务已被抢光
        @BindView(R.id.item_pb)
        ProgressBar    mItemPb;
        @BindView(R.id.item_pb_tv)
        TextView       mItemPbTv;
        @BindView(R.id.item_pb_layout)
        RelativeLayout mItemPbLayout;//进度条
        @BindView(R.id.item_v)
        View           mItemV;
        @BindView(R.id.item_complete_reward_layout)             //已领取任务显示的layout
        RelativeLayout mItemCompleteRewardLayout;
        @BindView(R.id.item_complete_reward_tv)
        TextView       mItemCompleteRewardTv;    //已领取任务显示的tv

        private RxDownload            mRxDownload;
        private DownloadController    mDownloadController;
        private Subscription          mSubscription;
        private TaskBean.UserTaskList mBean;

        private Context mContext;

        public ItemViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            parent.getContext();
            mContext = parent.getContext();

            mRxDownload = RxDownload.getInstance()
                    .context(mContext)
                    .autoInstall(true)  // 下载完成自动安装
                    .maxDownloadNumber(3);//最大下载数量

            mDownloadController = new DownloadController(mItemPbLayout, mItemRewardTv, mItemOpenRewardTv, mItemRewardIv);


            mItemRewardTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDownloadController.handleClick(new DownloadController.Callback() {
                        @Override
                        public void startDownload() {
                            start();
                        }

                        @Override
                        public void pauseDownload() {
                            pause();
                        }

                        @Override
                        public void cancelDownload() {
                            cancel();
                        }

                        @Override
                        public void install() {
                            installApk();
                        }
                    });
                }
            });

            mItemAmountTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDownloadController.handleClick(new DownloadController.Callback() {
                        @Override
                        public void startDownload() {
                            start();
                        }

                        @Override
                        public void pauseDownload() {
                            pause();
                        }

                        @Override
                        public void cancelDownload() {
                            cancel();
                        }

                        @Override
                        public void install() {
                            installApk();
                        }
                    });
                }
            });

            //打开任务领取奖励
            mItemOpenRewardTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onOpenReward(getLayoutPosition() - 1);
                    }
                }
            });

        }

        public void setItemBeanData(TaskBean.UserTaskList bean) {
            this.mBean = bean;
        }


        private void updateProgressStatus(DownloadStatus status) {
            mItemPb.setIndeterminate(status.isChunked);
            mItemPb.setMax((int) status.getTotalSize());
            mItemPb.setProgress((int) status.getDownloadSize());

            int position = status.getPercent().length();
//            int position = status.getPercent().indexOf("\\.");
            if (position == 7) {
                mItemPbTv.setText("100.00%");
            } else {
                mItemPbTv.setText(status.getPercent());
            }
            //            mSize.setText(status.getFormatStatusString());

        }

        private void installApk() {

            //这里安装应用，需要进行静默安装  ,调用修改即时任务接口，该任务已经完成
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onInstallApk(getLayoutPosition() - 1);
            }


        }

        private void start() {
            String url = mBean.getTaskUrl();
            String saveName = null;
            String savePath = null;
            if (mBean.getRecord() != null) {
                saveName = mBean.getRecord().getSaveName();
                savePath = mBean.getRecord().getSavePath();
            } else {
                saveName = mBean.getSaveName();
            }

            mBean.setStart(true);

            //点击taskAdapter中item开始下载时，用于修改底部UI
            EventBusUtil.postInfoEvent(Contants.SIX, new EventMessage(mBean.getAppPackeage()));

            //            RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
            RxPermissions.getInstance(mContext)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .doOnNext(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (!granted) {
                                throw new RuntimeException("no permission");
                            }
                        }
                    })
                    .compose(mRxDownload.transformService(url, saveName, savePath))
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            Toast.makeText(mContext, "下载开始", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void pause() {
            mRxDownload.pauseServiceDownload(mBean.getTaskUrl()).subscribe();
        }

        private void cancel() {
            mRxDownload.cancelServiceDownload(mBean.getTaskUrl()).subscribe();
        }

        private void delete() {
            mRxDownload.deleteServiceDownload(mBean.getTaskUrl())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            Utils.unSubscribe(mSubscription);
                        }
                    });
        }
    }

    static class ImageViewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_task_head_iv)
        ImageView mItemTaskHeadIv;

        ImageViewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_hint)
        ImageView      mIvHint;
        @BindView(R.id.no_task_layout)
        RelativeLayout mNoTaskLayout;

        EmptyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
