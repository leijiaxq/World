package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.WeixinInfoBean;
import com.vooda.world.model.ILoginModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.LoginModel;
import com.vooda.world.view.ILoginView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe
 */

public class LoginPresenter implements BasePresenter {


    private Context     mContext;
    private ILoginView  mILoginView;
    private ILoginModel mILoginModel;

    public LoginPresenter(Context context, ILoginView iLoginView) {
        mContext = context;
        mILoginView = iLoginView;
        mILoginModel = new LoginModel();
    }


    //登录
    public void LoginUser(String NiceName, String HeadUrl, String OpenID, String DeviceNumber, String UnionID) {
        mILoginModel.loginUser(NiceName, HeadUrl, OpenID, DeviceNumber, UnionID, new OnBaseCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                mILoginView.setLoginResultData(loginBean);
            }

            @Override
            public void onFailed(String msg) {
                mILoginView.networkFailed(msg);
            }
        });
    }

    //微信登录获取token
    public void accessTokenUrl(String appid, String secret, String code, String grant_type) {
        mILoginModel.accessTokenUrl(appid, secret, code, grant_type, new OnBaseCallBack<AccessTokenBean>() {
            @Override
            public void onSuccess(AccessTokenBean bean) {
                mILoginView.setAccessTokenResult(bean);
            }

            @Override
            public void onFailed(String msg) {
                mILoginView.setAccessTokenFailed(msg);

            }
        });
    }

    //微信登录刷新token
    public void refreshTokenUrl(String appid, String refresh_token, String grant_type) {
        mILoginModel.refreshTokenUrl(appid, refresh_token, grant_type, new OnBaseCallBack<RefreshTokenBean>() {
            @Override
            public void onSuccess(RefreshTokenBean bean) {
                mILoginView.setRefreshTokenResult(bean);
            }

            @Override
            public void onFailed(String msg) {
                mILoginView.setAccessTokenFailed(msg);

            }
        });
    }


    //微信登录通过token获取微信用户信息
    public void getWeixinInfoByToken(String access_token, String openid) {
        mILoginModel.getWeixinInfoByToken(access_token, openid, new OnBaseCallBack<WeixinInfoBean>() {
            @Override
            public void onSuccess(WeixinInfoBean bean) {
                mILoginView.setWeixinInfoByTokenResult(bean);
            }

            @Override
            public void onFailed(String msg) {
                mILoginView.setWeixinInfoByTokenFailed(msg);

            }
        });
    }




    @Override
    public void destroy() {
        mILoginModel.destroy();
    }
}
