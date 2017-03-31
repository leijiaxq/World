package com.vooda.world.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.vooda.world.R;
import com.vooda.world.manager.SystemBarTintManager;
import com.vooda.world.ui.widget.dialog.ZProgressHUD;
import com.vooda.world.utils.ActivityStackUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe   Activity基类
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    protected Context  mContext;
    private   Unbinder bind;
//    private   Dialog   mDialog;
    private  ZProgressHUD mZProgressHUD;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        ActivityStackUtil.addActivity(this);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initVariables();
        initViews(savedInstanceState);
        setTranslucentStatus(R.color.blue_theme);
        loadData();
    }

    /**
     * 设置状态栏背景状态
     */
    protected void setTranslucentStatus(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //此处可以重新指定状态栏颜色
            tintManager.setStatusBarTintResource(color);
        }
    }

    /*
     * 获取布局资源
     * */
    protected abstract int getLayoutId();

    /*
    * 初始化参数
    * */
    protected abstract void initVariables();

    /*
    * 初始化视图
    * */
    protected abstract void initViews(Bundle savedInstanceState);

    /*
    * 载入数据
    * */
    protected abstract void loadData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        ActivityStackUtil.removeActivity(this);

    }



    public void showProgressDialog(String message) {
        if (mZProgressHUD == null) {
            synchronized (this) {
                if (mZProgressHUD == null) {
                    mZProgressHUD = new ZProgressHUD(this);
                }
            }

        }
        mZProgressHUD.setMessage(message);
        mZProgressHUD.show();



    }



    public void hideProgressDialog() {
        if (mZProgressHUD != null && mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }

    }


//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}

