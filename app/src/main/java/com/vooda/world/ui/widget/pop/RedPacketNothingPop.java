package com.vooda.world.ui.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.vooda.world.R;

/**
 * Created by leijiaxq
 * Data       2016/12/28 9:36
 * Describe   没抢到红包
 */


public class RedPacketNothingPop extends PopupWindow implements View.OnClickListener {

    private Activity mContext;

    public RedPacketNothingPop(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.pop_red_packet_nothing, null);
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
        this.setOnDismissListener(new RedPacketNothingPop.poponDismissListener());
        view.findViewById(R.id.pop_confirm_btn).setOnClickListener(this);
        view.findViewById(R.id.pop_delete_iv).setOnClickListener(this);

        /*TextView tv = (TextView) view.findViewById(R.id.pop_content_tv);
        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_theme));

        builder.setSpan(blueSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);*/
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
    }

    private OnPopListenter mOnPopListenter;

    public void setOnPopListenter(OnPopListenter l) {
        mOnPopListenter = l;
    }

}
