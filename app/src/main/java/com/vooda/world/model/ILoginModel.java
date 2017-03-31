package com.vooda.world.model;

import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.WeixinInfoBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/29 10:04
 * Describe
 */


public interface ILoginModel extends IBaseModel {

    //微信登录,调用自己服务器接口
    void loginUser(String NiceName, String HeadUrl, String OpenID, String DeviceNumber, String UnionID, OnBaseCallBack<LoginBean> onBaseCallBack);

    //    微信登录获取token
    void accessTokenUrl(String appid, String secret, String code, String grant_type, OnBaseCallBack<AccessTokenBean> onBaseCallBack);

    //    微信登录刷新token
    void refreshTokenUrl(String appid, String refresh_toke, String grant_type, OnBaseCallBack<RefreshTokenBean> onBaseCallBack);

    //   微信登录,通过Token获取用户信息
    void getWeixinInfoByToken(String access_token, String openid, OnBaseCallBack<WeixinInfoBean> onBaseCallBack);
}
