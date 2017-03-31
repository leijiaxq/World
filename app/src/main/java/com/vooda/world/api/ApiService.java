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

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe
 */
public interface ApiService {

    //获取任务列表
    @FormUrlEncoded
    @POST("/api/GetMyTask.jsp")
    //    @POST("/api/GetMyTask")
    Observable<TaskBean> getTaskList(@Field("UserID") int userid);

    //获取好友列表数据
    @FormUrlEncoded
    @POST("/api/GetMyFrienfs.jsp")
    //    @POST("/api/GetMyFrienfs")
    Observable<MyFriendBean> getFriendList(@Field("UserID") int userid, @Field("PageIndex") int pageIndex);

    //获取好友任务详情列表数据
    @FormUrlEncoded
    @POST("/api/GetMyFriendsInfo.jsp")
    //    @POST("/User/MyFriendDateils")
    Observable<FriendInfoBean> getFriendInfoList(@Field("UserID") int userid, @Field("FriendID") int FriendID, @Field("PageIndex") int pageIndex);

    //    用户登录
    @FormUrlEncoded
    @POST("/api/OpenLogin.jsp")
    //    @POST("/api/OpenLogin")
    Observable<LoginBean> loginUser(@Field("NiceName") String NiceName, @Field("HeadUrl") String HeadUrl, @Field("OpenID") String OpenID, @Field("DeviceNumber") String DeviceNumber, @Field("UnionID") String UnionID);


    //    收支明细
    @FormUrlEncoded
    @POST("/api/GetBalancePay.jsp")
    //    @POST("/api/GetBalancePay")
    Observable<PaymentsBean> getPaymentsDetail(@Field("UserID") int userid, @Field("PageIndex") int pageIndex);

    //    正在进行中的任务
    @FormUrlEncoded
    @POST("/api/GetMyTaskList.jsp")
    //    @POST("/api/GetMyTaskList")
    Observable<TaskBean> getGongingTaskList(@Field("UserID") int userid, @Field("PageIndex") int pageIndex, @Field("Type") int Type);


    //    已完成的任务
    @FormUrlEncoded
    @POST("/api/GetMyTaskList.jsp")
    //    @POST("/api/GetMyTaskList")
    Observable<TaskBean> getCompletedTaskList(@Field("UserID") int userid, @Field("PageIndex") int pageIndex, @Field("Type") int Type);

    //   获取用户钱包数据
    @FormUrlEncoded
    @POST("/api/GetMyBalance.jsp")
    //    @POST("/api/GetMyBalance")
    Observable<ExchangeBean> getWalletDetail(@Field("UserID") int userid);

    //   获取红包列表数据
    @FormUrlEncoded
    @POST("/api/RedRecord.jsp")
    Observable<RedPacketBean> getRedPacketDetail(@Field("UserID") int userid/*, @Field("PageIndex") int pageIndex*/);


    //   抢红包
    @FormUrlEncoded
    @POST("/api/RobRed.jsp")
    //    @POST("/api/RobRed")
    Observable<RedRobBean> getRobRed(@Field("UserID") int userid);

    //   打开红包
    @FormUrlEncoded
    @POST("/api/OpenReward")
    Observable<RedOpenBean> getOpenReward(@Field("UserID") int userid, @Field("URedMoney") String URedMoney);

    //   获取提现金额配置
    @FormUrlEncoded
    @POST("/api/GetWebBalance.jsp")
    //    @POST("/api/GetWebBalance")
    Observable<WithdrawalsBean> getWebBalance(@Field("UserID") int userid);

    //   余额提现
    @FormUrlEncoded
    @POST("/api/BalanceT.jsp")
    //    @POST("/api/BalanceT")
    Observable<WithdrawalsResultBean> getBalanceT(@Field("UserID") int userid, @Field("Amoney") String Amoney);

    //   修改任务的即时状态
    @FormUrlEncoded
    @POST("/api/UpdateUserTask.jsp")
    //    @POST("/api/UpdateUserStaus")
    Observable<ReceivedRewardBean> updateUserStaus(@Field("UserID") int userid, @Field("TaskID") int TaskID, @Field("IsOpen") int IsOpen, @Field("IsDownload") int IsDownload, @Field("IsInstall") int IsInstall);

    //   修改任务已通过下载的APK文件安装
    @FormUrlEncoded
    @POST("/api/UpdateInstall")
    Observable<BaseRequestBean> installAPPforAPK(@Field("UserID") int userid, @Field("TaskID") int TaskID, @Field("IsInstall") int IsInstall);


    //  生成带参数的二维码
    @FormUrlEncoded
    @POST("/api/GenerateCode")
    Observable<CodeBean> generateCode(@Field("UserID") int userid);


    //   本地判断是否已经分享过二维码链接(生成过二维码)(服务器端需要)
    @FormUrlEncoded
    @POST("/api/IsCodeExist")
    Observable<BaseRequestBean> isCodeExist(@Field("UserID") int userid, @Field("IsInstall") int IsInstall);


    //   获取用户是否关注微信公众号
    @FormUrlEncoded
    @POST("/api/IsFollow.jsp")
    Observable<FollowBean> isFollowWeixinNumber(@Field("UserID") int userid);


    //   微信登录获取token
    @GET("/sns/oauth2/access_token")
    Observable<AccessTokenBean> accessTokenUrl(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    //   微信登录刷新token
    @GET("/sns/oauth2/refresh_token")
    Observable<RefreshTokenBean> refreshTokenUrl(@Query("appid") String appid, @Query("refresh_token") String refresh_token, @Query("grant_type") String grant_type);


    //   微信登录,通过Token获取用户信息
    @GET("/sns/userinfo")
    Observable<WeixinInfoBean> getWeixinInfoByToken(@Query("access_token") String access_token, @Query("openid") String openid);


    //   用于检测新版本
    @GET("/api/appUpdate/getAppInfo.jsp")
    Observable<NewVersionBean> checkNewVersion();
}
