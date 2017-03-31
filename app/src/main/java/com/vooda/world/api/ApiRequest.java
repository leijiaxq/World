package com.vooda.world.api;

import com.vooda.world.bean.AccessTokenBean;
import com.vooda.world.bean.BaseRequestBean;
import com.vooda.world.bean.CodeBean;
import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;
import com.vooda.world.bean.FriendInfoBean;
import com.vooda.world.bean.LoginBean;
import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.bean.NewVersionBean;
import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.bean.ReceivedRewardBean;
import com.vooda.world.bean.RedOpenBean;
import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.bean.RedRobBean;
import com.vooda.world.bean.RefreshTokenBean;
import com.vooda.world.bean.TaskBean;
import com.vooda.world.bean.WeixinInfoBean;
import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe
 */


public class ApiRequest {


    /**
     * 通用的请求设置
     *
     * @return
     */
    private static ApiService ApiRequest() {
        return ApiManager.getInstance().initRetrofit().create(ApiService.class);
    }

    /**
     * 微信登录获取信息用的
     *
     * @return
     */
    private static ApiService ApiRequestWeixinLogin() {
        return ApiManager.getInstance().initWeixinLoginRetrofit().create(ApiService.class);
    }

    //获取任务列表数据
    public static void getTaskList(int userID, Subscriber<TaskBean> subscriber) {
        ApiRequest().getTaskList(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取好友列表数据
    public static void getFriendList(int userID, int pageIndex, Subscriber<MyFriendBean> subscriber) {
        ApiRequest().getFriendList(userID, pageIndex)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //获取好友列表数据
    public static void getFriendInfoList(int userID, int FriendID, int pageIndex, Subscriber<FriendInfoBean> subscriber) {
        ApiRequest().getFriendInfoList(userID, FriendID, pageIndex)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //用户登录
    public static void loginUser(String NiceName, String HeadUrl, String OpenID, String DeviceNumber, String UnionID, Subscriber<LoginBean> subscriber) {
        ApiRequest().loginUser(NiceName, HeadUrl, OpenID, DeviceNumber, UnionID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //获取收支详情数据
    public static void getPaymentsDetail(int userID, int pageIndex, Subscriber<PaymentsBean> subscriber) {
        ApiRequest().getPaymentsDetail(userID, pageIndex)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 正在进行中的任务
    public static void getGongingTaskList(int userID, int pageIndex, int Type, Subscriber<TaskBean> subscriber) {
        ApiRequest().getGongingTaskList(userID, pageIndex, Type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //    已完成的任务
    public static void getCompletedTaskList(int userID, int pageIndex, int Type, Subscriber<TaskBean> subscriber) {

        ApiRequest().getCompletedTaskList(userID, pageIndex, Type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //    获取用户钱包数据
    public static void getWalletDetail(int userID, Subscriber<ExchangeBean> subscriber) {
        ApiRequest().getWalletDetail(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //    获取红包列表数据
    public static void getRedPacketDetail(int userID, /*int pageIndex,*/ Subscriber<RedPacketBean> subscriber) {
        ApiRequest().getRedPacketDetail(userID/*, pageIndex*/)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //    抢红包
    public static void getRobRed(int userID, Subscriber<RedRobBean> subscriber) {
        ApiRequest().getRobRed(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //    打开红包
    public static void getOpenReward(int userID, String URedMoney, Subscriber<RedOpenBean> subscriber) {
        ApiRequest().getOpenReward(userID, URedMoney)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   获取提现金额配置
    public static void getWebBalance(int userID, Subscriber<WithdrawalsBean> subscriber) {
        ApiRequest().getWebBalance(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //   余额提现
    public static void getBalanceT(int userID, String Amoney, Subscriber<WithdrawalsResultBean> subscriber) {
        ApiRequest().getBalanceT(userID, Amoney)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   修改任务的即时状态
    public static void updateUserStaus(int userID, int TaskID, int IsOpen, int IsDownload, int IsInstall, Subscriber<ReceivedRewardBean> subscriber) {
        ApiRequest().updateUserStaus(userID, TaskID, IsOpen, IsDownload, IsInstall)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   修改任务已通过下载的APK文件安装
    public static void installAPPforAPK(int userID, int TaskID, int IsInstall, Subscriber<BaseRequestBean> subscriber) {
        ApiRequest().installAPPforAPK(userID, TaskID, IsInstall)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   生成带参数的二维码
    public static void generateCode(int userID, Subscriber<CodeBean> subscriber) {
        ApiRequest().generateCode(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   本地判断是否已经分享过二维码链接(生成过二维码)(服务器端需要)
    public static void isCodeExist(int userID, int IsInstall, Subscriber<BaseRequestBean> subscriber) {
        ApiRequest().isCodeExist(userID, IsInstall)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   获取用户是否关注微信公众号
    public static void isFollowWeixinNumber(int userID, Subscriber<FollowBean> subscriber) {
        ApiRequest().isFollowWeixinNumber(userID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //微信登录,获取token
    public static void accessTokenUrl(String appid, String secret, String code, String grant_type, Subscriber<AccessTokenBean> subscriber) {
        ApiRequestWeixinLogin().accessTokenUrl(appid, secret, code, grant_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //微信登录,刷新token
    public static void refreshTokenUrl(String appid, String refresh_token, String grant_type, Subscriber<RefreshTokenBean> subscriber) {
        ApiRequestWeixinLogin().refreshTokenUrl(appid, refresh_token, grant_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    //   微信登录,通过Token获取用户信息
    public static void getWeixinInfoByToken(String access_token, String openid, Subscriber<WeixinInfoBean> subscriber) {
        ApiRequestWeixinLogin().getWeixinInfoByToken(access_token, openid )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //   用于检测新版本
    public static void checkNewVersion(Subscriber<NewVersionBean> subscriber) {
        ApiRequest().checkNewVersion()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
