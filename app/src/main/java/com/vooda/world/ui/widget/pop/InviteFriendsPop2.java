package com.vooda.world.ui.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vooda.world.R;

/**
 * @desc: 邀请好友一起玩
 * @author: zhujun
 * @date: 2016/12/22 0022  下午 5:08
 */
public class InviteFriendsPop2 extends PopupWindow implements View.OnClickListener {
    private Activity mContext;

    public InviteFriendsPop2(Context context, boolean flag) {
        super(context);
        this.mContext = (Activity) context;
        initView(flag);
    }

    //flag 用于判断是点击了开始任务还是  邀请好友
    private void initView(boolean flag) {
        View view = View.inflate(mContext, R.layout.pop_invate_friends2, null);
        this.setContentView(view);
        this.setFocusable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.pop_center_anim);
        ColorDrawable colorDrawable = new ColorDrawable(0x40000000);
        this.setBackgroundDrawable(colorDrawable);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = 0.5f;
        mContext.getWindow().setAttributes(params);
        this.setOnDismissListener(new poponDismissListener());
        Button popCancelBtn = (Button) view.findViewById(R.id.pop_cancle_btn);
        popCancelBtn.setOnClickListener(this);
        view.findViewById(R.id.pop_confirm_btn).setOnClickListener(this);
        view.findViewById(R.id.pop_confirm_btn2).setOnClickListener(this);
        view.findViewById(R.id.pop_delete_iv).setOnClickListener(this);

        if (flag) {
            view.findViewById(R.id.pop_invate_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.pop_invate_layout2).setVisibility(View.GONE);

        } else {
            view.findViewById(R.id.pop_invate_layout).setVisibility(View.GONE);
            view.findViewById(R.id.pop_invate_layout2).setVisibility(View.VISIBLE);
        }

        TextView tv = (TextView) view.findViewById(R.id.pop_tv2);
        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red2));
        ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red2));
        ForegroundColorSpan blueSpan3 = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red2));
        ForegroundColorSpan blueSpan4 = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_red2));

        builder.setSpan(blueSpan, 63, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(blueSpan2, 73, 78, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(blueSpan3, 85, 90, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(blueSpan4, 97, 103, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_confirm_btn:
                this.dismiss();
                if (mOnPopListenter != null) {
                    mOnPopListenter.onConfirm();
                }
                break;
            case R.id.pop_confirm_btn2:
                this.dismiss();
                if (mOnPopListenter != null) {
                    mOnPopListenter.onConfirm();
                }
                break;
            case R.id.pop_cancle_btn:
                this.dismiss();
                if (mOnPopListenter != null) {
                    mOnPopListenter.onCancel();
                }
                break;
            case R.id.pop_delete_iv:
                this.dismiss();
                break;
        }

    }

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

        void onCancel();
    }

    private OnPopListenter mOnPopListenter;

    public void setOnPopListenter(OnPopListenter l) {
        mOnPopListenter = l;
    }
}
