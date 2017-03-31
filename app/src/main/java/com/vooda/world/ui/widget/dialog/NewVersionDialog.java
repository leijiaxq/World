package com.vooda.world.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vooda.world.R;

/**
 * Created by leijiaxq
 * Data       2017/1/17 17:01
 * Describe   新版本展示的信息
 */


public class NewVersionDialog extends Dialog {

    Context context;

    public NewVersionDialog(Context context,String str) {
        super(context);
        this.context = context;

        init(str);
    }

    private void init(String str) {
        initWindow();
        View contentView = View.inflate(context, R.layout.pop_cash_hint, null);
        setCanceledOnTouchOutside(false);
        setContentView(contentView);

        TextView tv = (TextView) contentView.findViewById(R.id.pop_content_tv);

        if (!TextUtils.isEmpty(str)) {




            tv.setText(str);
        }

        contentView.findViewById(R.id.pop_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }

                if (mOnPopListenter != null) {
                    mOnPopListenter.onConfirm();
                }
            }
        });

    }

    /**
     * 初始化window参数
     */
    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        // 设置显示动画
        dialogWindow.setWindowAnimations(R.style.pop_center_anim);
    }

    public interface OnPopListenter {
        void onConfirm();
    }

    private OnPopListenter mOnPopListenter;

    public void setOnPopListenter(OnPopListenter l) {
        mOnPopListenter = l;
    }

}
