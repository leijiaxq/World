package com.vooda.world.model.impl;

import android.text.TextUtils;

import com.vooda.world.api.ApiRequest;
import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.WeixinInfoBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.model.ILoginModel;
import com.vooda.world.model.callback.OnBaseCallBack;

import rx.Subscriber;

/**
 * Created by leijiaxq
 * Data       2016/12/29 10:59
 * Describe
 */

public class LoginModel extends SubscriptionModel implements ILoginModel {


    //登录
    @Override
    public void loginUser(String NiceName, String HeadUrl, String OpenID, String DeviceNumber, String UnionID, final OnBaseCallBack<LoginBean> onBaseCallBack) {
        Subscriber subscriber = new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean loginBean) {
                if (Contants.OK.equals(loginBean.getResult())) {
                    onBaseCallBack.onSuccess(loginBean);
                } else {
                    onBaseCallBack.onFailed(loginBean.getMessage());
                }
            }
        };

        ApiRequest.loginUser(NiceName, HeadUrl, OpenID, DeviceNumber, UnionID, subscriber);
        addSubscription(subscriber);
    }

    //微信登录获取token
    @Override
    public void accessTokenUrl(String appid, String secret, String code, String grant_type, final OnBaseCallBack<AccessTokenBean> onBaseCallBack) {
        final Subscriber subscriber = new Subscriber<AccessTokenBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AccessTokenBean bean) {
                if (!TextUtils.isEmpty(bean.getAccess_token())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getErrmsg());
                    //                    onBaseCallBack.onFailed(publicBean.getMessage());
                }
            }
        };

        ApiRequest.accessTokenUrl( appid,  secret,  code,  grant_type, subscriber);
        addSubscription(subscriber);
    }

    //微信登录刷新token
    @Override
    public void refreshTokenUrl(String appid, String refresh_toke, String grant_type, final OnBaseCallBack<RefreshTokenBean> onBaseCallBack) {
        final Subscriber subscriber = new Subscriber<RefreshTokenBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshTokenBean bean) {
                if (!TextUtils.isEmpty(bean.getAccess_token())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getErrmsg());
                }
            }
        };

        ApiRequest.refreshTokenUrl( appid,  refresh_toke,  grant_type, subscriber);
        addSubscription(subscriber);
    }

    //   微信登录,通过Token获取用户信息
    @Override
    public void getWeixinInfoByToken(String access_token, String openid, final OnBaseCallBack<WeixinInfoBean> onBaseCallBack) {
        final Subscriber subscriber = new Subscriber<WeixinInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeixinInfoBean bean) {
                if (!TextUtils.isEmpty(bean.getOpenid())) {
                    onBaseCallBack.onSuccess(bean);
                } else {
                    onBaseCallBack.onFailed(bean.getErrmsg());
                }
            }
        };

        ApiRequest.getWeixinInfoByToken( access_token,  openid, subscriber);
        addSubscription(subscriber);
    }



    @Override
    public void destroy() {
        onUnsubscribe();
    }
}
