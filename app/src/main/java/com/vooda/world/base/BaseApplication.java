package com.vooda.world.base;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vooda.world.bean.UserInfoBean;
import com.vooda.world.contant.Contants;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:20
 * Describe   应用程序的Application
 */


public class BaseApplication extends Application {

    public static BaseApplication mApplication;

    //保存的用户信息
    private UserInfoBean mUserInfoBean;

//    private static Handler mMainHandler;
    public static  IWXAPI  api;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
//        mMainHandler = new Handler();

        api = WXAPIFactory.createWXAPI(this, Contants.APP_ID, true);
        api.registerApp(Contants.APP_ID);
    }

    public static BaseApplication getInstance() {
        return mApplication;
    }


    public UserInfoBean getUserInfoBean() {
        return mUserInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.mUserInfoBean = userInfoBean;
    }


//    public static Handler getMainHandler() {
//        return mMainHandler;
//    }


}
