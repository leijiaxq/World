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
import com.vooda.world.ui.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leijiaxq
 * Data       2016/12/29 17:58
 * Describe
 */

public class SelectMoneyPop extends PopupWindow {
    private Activity          mContext;
    private WheelView         mWheelView;
    private ArrayList<String> mDatas;

    public SelectMoneyPop(Context context,List<String> datas) {
        super(context);
        this.mContext = (Activity) context;
        mDatas = (ArrayList<String>) datas;
        initView();
    }

    private void initView() {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.pop_select_money, null);
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

        mWheelView = (WheelView) view.findViewById(R.id.pop_wheelView);
        mWheelView.setData(mDatas);
        mWheelView.setDefault(0);
        view.findViewById(R.id.pop_conpleted_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mMoneyListener != null) {
                    mMoneyListener.onItem(getSelectedData());
                }
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

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
            params.alpha = 1f;
            mContext.getWindow().setAttributes(params);
        }

    }



    public String getSelectedData() {
        if (mWheelView == null) {
            return null;
        }
        return mWheelView.getSelectedText();
    }


    MoneyListener mMoneyListener;

    public void setMoneyListener(MoneyListener pMoneyListener) {
        mMoneyListener = pMoneyListener;
    }

    public interface MoneyListener {
        void onItem(String text);
    }
}
