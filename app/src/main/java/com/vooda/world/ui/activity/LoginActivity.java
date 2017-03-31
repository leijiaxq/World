package com.vooda.world.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vooda.world.R;
import com.vooda.world.base.BaseActivity;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.UserInfoBean;
import com.vooda.world.bean.WeixinInfoBean;
import com.vooda.world.contant.Contants;
import com.vooda.world.presenter.LoginPresenter;
import com.vooda.world.utils.DeviceUtil;
import com.vooda.world.utils.SPUtil;
import com.vooda.world.utils.ToastUtil;
import com.vooda.world.view.ILoginView;
import com.vooda.world.wxapi.WXEntryActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vooda.world.base.BaseApplication.api;

/**
 * Created by leijiaxq
 * Data       2016/12/29 9:35
 * Describe   登录页面
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.weixin_iv)
    ImageView mWeixinIv;

    private LoginPresenter mPresenter;
    private UserInfoBean   mUserInfoBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mPresenter = new LoginPresenter(this, this);


        int userID = (int) SPUtil.get(this, Contants.USER_ID, 0);
        //        LogUtil.d("----------" + userID);

        if (userID != 0) {
            getForeverUserInfo();
        }


        //        if (userID != 0) {  //表示用户已经注册登录过，并且本地保存有信息，就直接登录//  ------现在不需要延时
        //            showProgressDialog("正在登录");
        //            BaseApplication.getMainHandler().postDelayed(new Runnable() {
        //                @Override
        //                public void run() {
        //                   /* hideProgressDialog();
        //                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //                    startActivity(intent);
        //                    finish();*/
        //
        //                    //                    getForeverUserInfo();
        //                }
        //            }, Contants.DELAYED_TIME);
        //
        //        }
    }


    //取出保存的用户数据，进行登录
    private void getForeverUserInfo() {
        mUserInfoBean = new UserInfoBean();
        int userID = (int) SPUtil.get(mContext, Contants.USER_ID, 0);
        String userName = (String) SPUtil.get(mContext, Contants.USER_NAME, "");
        String userHeadUrl = (String) SPUtil.get(mContext, Contants.USER_HEADURL, "");
        //        String userPhone = (String) SPUtil.get(mContext, Contants.USER_PHONE, "");
        //        int userStatus = (int) SPUtil.get(mContext, Contants.USER_STATUS, 0);
        //        int userFollow = (int) SPUtil.get(mContext, Contants.USER_FOLLOW, 0);
        String userDeviceNumber = (String) SPUtil.get(mContext, Contants.USER_DEVICENUMBER, "");
        String userOpenID = (String) SPUtil.get(mContext, Contants.USER_OPENID, "");
        String userUnionID = (String) SPUtil.get(mContext, Contants.USER_UNIONID, "");


        mUserInfoBean.setUserID(userID);
        mUserInfoBean.setUserName(userName);
        mUserInfoBean.setHeadUrl(userHeadUrl);
        //        mUserInfoBean.setPhone(userPhone);
        //        mUserInfoBean.setIsStatus(userStatus);
        //        mUserInfoBean.setIsfollow(userFollow);
        mUserInfoBean.setDeviceNumber(userDeviceNumber);
        mUserInfoBean.setOpenID(userOpenID);
        mUserInfoBean.setUnionID(userUnionID);
        //        BaseApplication.getInstance().setUserInfoBean(mUserInfoBean);

        mPresenter.LoginUser(mUserInfoBean.getUserName(), mUserInfoBean.getHeadUrl(), mUserInfoBean.getOpenID(), mUserInfoBean.getDeviceNumber(), mUserInfoBean.getUnionID());
    }


    @OnClick(R.id.weixin_iv)
    void loginClick(View view) {

       /* Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/

        //        mWeixinIv.setClickable(false);

        if (BaseApplication.api == null) {
            BaseApplication.api = WXAPIFactory.createWXAPI(this, Contants.APP_ID, true);
            api.registerApp(Contants.APP_ID);
        }
        if (!api.isWXAppInstalled()) {
            ToastUtil.showShort(LoginActivity.this, "您手机尚未安装微信，请安装后再登录");
            return;
        }

        showProgressDialog("正在登陆");

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_xb_live_state";//官方说明：用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        api.sendReq(req);


    }


    //用户登录成功返回的数据
    @Override
    public void setLoginResultData(LoginBean loginBean) {
        hideProgressDialog();
        if (loginBean == null) {
            ToastUtil.showShort(this, Contants.ERROR);
            return;
        }

        if (mUserInfoBean == null) {
            mUserInfoBean = new UserInfoBean();
        }

        mUserInfoBean.setUserID(Integer.valueOf(loginBean.getUserID()));
        //        mUserInfoBean.setHeadUrl(loginBean.getHeadUrl());
        mUserInfoBean.setPhone(loginBean.getPhone());
        mUserInfoBean.setRegisterDate(loginBean.getRegisterDate());
        //        mUserInfoBean.setIsStatus(loginBean.getIsStatus());
        mUserInfoBean.setIsfollow(loginBean.getIsfollow());
        //        mUserInfoBean.setDeviceNumber(loginBean.getDeviceNumber());
        //        mUserInfoBean.setOpenID(loginBean.getOpenID());

        BaseApplication.getInstance().setUserInfoBean(mUserInfoBean);

        //   登录成功后,保存用户信息,并跳转到MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //网络请求失败
    @Override
    public void networkFailed(String msg) {
        hideProgressDialog();
        //        ToastUtil.showShort(this, Contants.FAILURE);
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(LoginActivity.this, msg);
        } else {
            ToastUtil.showShort(LoginActivity.this, Contants.FAILURE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //这里的判断是为了区分如果不是不是从WXEntryActivity页面销毁重启的，不走下面的代码
        if (WXEntryActivity.resp != null) {
            if (WXEntryActivity.resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {

                if (mPresenter != null) {
                    mPresenter.accessTokenUrl(Contants.APP_ID, Contants.APP_SECRET, WXEntryActivity.code, "authorization_code");
                }
            }
        }
    }


    //微信登录获取token成功返回的结果
    @Override
    public void setAccessTokenResult(AccessTokenBean bean) {

        if (bean != null) {
            if (mPresenter != null) {
                mPresenter.refreshTokenUrl(Contants.APP_ID, bean.getRefresh_token(), "refresh_token");
            } else {
                hideProgressDialog();
            }
        } else {
            hideProgressDialog();
        }
    }


    //微信登录刷新token成功返回的结果
    @Override
    public void setRefreshTokenResult(RefreshTokenBean bean) {

        if (bean != null) {
            String access_token = bean.getAccess_token();
            String openid = bean.getOpenid();
            if (mPresenter != null) {
                mPresenter.getWeixinInfoByToken(access_token, openid);
            } else {
                hideProgressDialog();
            }
        } else {
            hideProgressDialog();
        }
    }


    //微信登录获取或刷新token失败
    @Override
    public void setAccessTokenFailed(String msg) {
        hideProgressDialog();
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(LoginActivity.this, msg);
        }
    }

    //获取微信用户信息成功
    @Override
    public void setWeixinInfoByTokenResult(WeixinInfoBean bean) {
        if (bean != null) {
            String headimgurl = bean.getHeadimgurl();
            String nickname = bean.getNickname();
            String openid = bean.getOpenid();
            String unionid = bean.getUnionid();

            mUserInfoBean = new UserInfoBean();

            mUserInfoBean.setOpenID(openid);
            mUserInfoBean.setHeadUrl(headimgurl);
            mUserInfoBean.setUserName(nickname);
            mUserInfoBean.setUnionID(unionid);
            //设备ID
            String androidID = DeviceUtil.getAndroidID(this);
            mUserInfoBean.setDeviceNumber(androidID);

            mPresenter.LoginUser(mUserInfoBean.getUserName(), mUserInfoBean.getHeadUrl(), mUserInfoBean.getOpenID(), mUserInfoBean.getDeviceNumber(), mUserInfoBean.getUnionID());
        }
    }

    //获取微信用户信息失败
    @Override
    public void setWeixinInfoByTokenFailed(String msg) {
        hideProgressDialog();
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showShort(LoginActivity.this, msg);
        }
    }

}
