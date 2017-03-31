package com.vooda.world.ui.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.vooda.world.R;

/**
 * Created by leijiaxq
 * Data       2016/12/29 17:58
 * Describe
 */

public class SelectSharePop extends PopupWindow {
    private Activity          mContext;

    public SelectSharePop(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_select_share, null);
        this.setContentView(view);
        this.setFocusable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
//        this.setBackgroundDrawable(colorDrawable);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_bottom_anim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x40000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = 0.2f;
        mContext.getWindow().setAttributes(params);
        this.setOnDismissListener(new poponDismissListener());


        //分享给朋友
        view.findViewById(R.id.pop_share_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mShareListener !=null) {
                    mShareListener.onItem(1);
                }
            }
        });

        view.findViewById(R.id.pop_share_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mShareListener !=null) {
                    mShareListener.onItem(2);
                }
            }
        });

        view.findViewById(R.id.pop_share_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


       /* mView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.pop_area_tv).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });*/


    }

    class poponDismissListener implements OnDismissListener {

        @Override
        public void onDismiss() {
            WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
            params.alpha = 1f;
            mContext.getWindow().setAttributes(params);
        }

    }


    ShareListener mShareListener;

    public void setShareListener(ShareListener pShareListener) {
        mShareListener = pShareListener;
    }

    public interface ShareListener {
        void onItem(int position);
    }
}
