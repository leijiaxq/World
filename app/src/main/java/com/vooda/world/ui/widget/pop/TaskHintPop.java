package com.vooda.world.ui.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vooda.world.R;

/**
 * Created by leijiaxq
 * Data       2016/12/28 9:36
 * Describe   打开APP领取奖励时，如果有提示内容，就弹出
 */


public class TaskHintPop extends PopupWindow {

    private Activity mContext;

    public TaskHintPop(Context context,String str) {
        super(context);
        this.mContext = (Activity) context;
        initView(str);
    }

    private void initView(String str) {
        View view = View.inflate(mContext, R.layout.pop_task_hint, null);
        this.setContentView(view);
        this.setFocusable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_center_anim);

//        ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
        ColorDrawable colorDrawable = new ColorDrawable(0x40000000);
        this.setBackgroundDrawable(colorDrawable);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = 0.5f;
        mContext.getWindow().setAttributes(params);
        this.setOnDismissListener(new TaskHintPop.poponDismissListener());
        view.findViewById(R.id.pop_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnPopListenter != null) {
                    mOnPopListenter.onConfirm();
                }
            }
        });

        TextView tv = (TextView) view.findViewById(R.id.pop_content_tv);

        tv.setText(str);
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_confirm_btn:
                this.dismiss();
                if (mOnPopListenter != null) {
                    mOnPopListenter.onConfirm();
                }
                break;

        }

    }*/

    class poponDismissListener implements OnDismissListener {

        @Override
        public void onDismiss() {
            WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
            params.alpha = 1f;
            mContext.getWindow().setAttributes(params);
        }

    }

    public interface OnPopListenter {
        void onConfirm();
    }

    private OnPopListenter mOnPopListenter;

    public void setOnPopListenter(OnPopListenter l) {
        mOnPopListenter = l;
    }

}
