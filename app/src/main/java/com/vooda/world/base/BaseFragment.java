package com.vooda.world.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vooda.world.ui.widget.dialog.ZProgressHUD;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe   Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected FragmentActivity mActivity;

    //Fragment当前状态是否可见
    private boolean isVisible;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private  ZProgressHUD mZProgressHUD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActivity = getActivity();
        initVariables();
        isPrepared = true;
        return initViews(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        lazyLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            lazyLoad();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoad();
    }


    /**
     * 初始化参数
     */
    protected abstract void initVariables();

    /**
     * 初始化视图
     */
    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 载入数据
     */
    protected abstract void loadData();

    /**
     * 懒加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();


    public void showProgressDialog(String message) {
        if (mZProgressHUD == null) {
            synchronized (this) {
                if (mZProgressHUD == null) {
                    mZProgressHUD = new ZProgressHUD(getContext());
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

}



