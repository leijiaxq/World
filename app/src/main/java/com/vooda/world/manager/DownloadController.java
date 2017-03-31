package com.vooda.world.manager;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vooda.world.R;

import zlc.season.rxdownload.entity.DownloadEvent;
import zlc.season.rxdownload.entity.DownloadFlag;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/11/22
 * Time: 15:18
 * FIXME
 */
public class DownloadController {
    private RelativeLayout mStatus;
    private TextView       mAction;
    private TextView       mActionOpen;
    private ImageView      mActionOpenIv;

    private DownloadState mState;


 /*   public DownloadResultListener mResultListener;
    private static boolean isFirst = true;*/

    public DownloadController(RelativeLayout status, TextView action, TextView actionOpen, ImageView actionOpenIv) {
        mStatus = status;
        mAction = action;
        mActionOpen = actionOpen;
        mActionOpenIv = actionOpenIv;
        setState(new Normal());
    }


    public void setState(DownloadState state) {
        mState = state;
        mState.setText(mStatus, mAction, mActionOpen, mActionOpenIv);
    }

    public void setEvent(DownloadEvent event) {
        int flag = event.getFlag();
        switch (flag) {
            case DownloadFlag.NORMAL:
                setState(new DownloadController.Normal());
                break;
            case DownloadFlag.WAITING:
                setState(new DownloadController.Waiting());
                break;
            case DownloadFlag.STARTED:
                setState(new DownloadController.Started());
                break;
            case DownloadFlag.PAUSED:
                setState(new DownloadController.Paused());
                break;
            case DownloadFlag.CANCELED:
                setState(new DownloadController.Canceled());
                break;
            case DownloadFlag.COMPLETED:
                setState(new DownloadController.Completed());
                break;
            case DownloadFlag.FAILED:
                setState(new DownloadController.Failed());
                break;
            case DownloadFlag.DELETED:
                setState(new DownloadController.Deleted());
                break;
        }
    }

    public void handleClick(Callback callback) {
        mState.handleClick(callback);
    }

    public interface Callback {
        void startDownload();

        void pauseDownload();

        void cancelDownload();

        void install();
    }

    static abstract class DownloadState {

        abstract void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv);

        abstract void handleClick(Callback callback);
    }

    public static class Normal extends DownloadState {

        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
            /*button.setText("下载");
            status.setText("");*/
            status.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        /*    buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);*/
        }

        @Override
        void handleClick(Callback callback) {
            callback.startDownload();
        }
    }

    public static class Waiting extends DownloadState {
        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
           /* button.setText("等待中");
            status.setText("等待中...");*/
            status.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setText("下载等待中");
            button.setBackgroundResource(R.drawable.selector_task_orange);
            buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);
        }

        @Override
        void handleClick(Callback callback) {
            callback.cancelDownload();
        }
    }

    public static class Started extends DownloadState {
        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
           /* button.setText("暂停");
            status.setText("下载中...");*/

            status.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);
        }

        @Override
        void handleClick(Callback callback) {
            callback.pauseDownload();
        }
    }

    public static class Paused extends DownloadState {
        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
           /* button.setText("继续");
            status.setText("已暂停");*/
            status.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setText("已暂停下载");
            button.setBackgroundResource(R.drawable.selector_task_orange);

            buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);
        }

        @Override
        void handleClick(Callback callback) {
            callback.startDownload();
        }
    }

    public static class Failed extends DownloadState {
        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
            /*button.setText("继续");
            status.setText("下载失败");*/
            status.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setText("下载失败");
            button.setBackgroundResource(R.drawable.selector_task_red);
            buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);
        }

        @Override
        void handleClick(Callback callback) {
            callback.startDownload();
        }
    }

    public static class Canceled extends DownloadState {
        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
          /*  button.setText("下载");
            status.setText("下载已取消");*/
            status.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setText("开始任务");
            button.setBackgroundResource(R.drawable.selector_task_orange);
            buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);
        }

        @Override
        void handleClick(Callback callback) {
            callback.startDownload();
        }
    }

    public static class Completed extends DownloadState {


        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
          /*  button.setText("安装");
            status.setText("下载已完成");*/

            status.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

            buttonOpen.setVisibility(View.VISIBLE);
            button.setText("打开得奖励");
            buttonOpen.setBackgroundResource(R.drawable.selector_task_blue);
            actionOpenIv.setVisibility(View.VISIBLE);


        }

        @Override
        void handleClick(Callback callback) {
            callback.install();
        }
    }

    public static class Deleted extends DownloadState {

        @Override
        void setText(RelativeLayout status, TextView button, TextView buttonOpen, ImageView actionOpenIv) {
           /* button.setText("下载");
            status.setText("下载已取消");*/
           /* status.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setText("下载已取消");
            button.setBackgroundResource(R.drawable.shape_task_bg_red);

            buttonOpen.setVisibility(View.GONE);
            actionOpenIv.setVisibility(View.GONE);*/

            status.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

//            buttonOpen.setVisibility(View.VISIBLE);

//            button.setText("打开得奖励");
//            buttonOpen.setBackgroundResource(R.drawable.shape_task_bg_blue);
//            actionOpenIv.setVisibility(View.VISIBLE);


        }

        @Override
        void handleClick(Callback callback) {
            callback.startDownload();
        }
    }
}
