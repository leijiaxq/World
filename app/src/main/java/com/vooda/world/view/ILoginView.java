package com.vooda.world.view;

import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.WeixinInfoBean;

/**
 * Created by leijiaxq
 * Data       2016/12/29 10:05
 * Describe
 */

public interface ILoginView extends IBaseView {

    void setLoginResultData(LoginBean loginBean);


    //微信登录获取token
    void setAccessTokenResult(AccessTokenBean bean);

    //微信登录获取或刷新token 失败
    void setAccessTokenFailed(String msg);

    //微信登录刷新token
    void setRefreshTokenResult(RefreshTokenBean bean);

    //获取微信用户信息成功
    void setWeixinInfoByTokenResult(WeixinInfoBean bean);

    //获取微信用户信息失败
    void setWeixinInfoByTokenFailed(String msg);
}
