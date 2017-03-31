package com.vooda.world.contant;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:35
 * Describe   常量相关
 */


public class Contants {

    /******************** 存储相关常量 ********************/
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    public static final String OK = "ok";

    public static final String ERROR   = "数据有误";
    public static final String FAILURE = "网络请求失败";

    //微信公众号的凭证
    public static final String PUBLIC_NUMBER_TOKEN = "aaaaaaaaa";

    //adapter 的类型
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;
    public static final int TYPE3 = 3;

    //分页加载，每页的个数
    public static final int SIZE = 10;

    //刷新数据延时时间
    public static final int REFRESH_TIME = 1000;


    //从登录也延时多长时间进入主页面
    public static final int DELAYED_TIME = 0;


    /**
     * intent 传递数据用的String
     */
    //用户提现可用的收入
    public static final String USER_CAN_BALANCE = "UserCanBalance";

    //用户提现成功后跳转到申请提现成功页面的数据  ----剩余金额
    public static final String USER_BALANCE = "UserBalance";

    //用户提现成功后跳转到申请提现成功页面的数据  ----提现的金额
    public static final String CAN_BALANCE = "CanBalance";

    //查看好友任务详情传递好友ID;
    public static final String FRIENDID = "FriendID";

    //查看好友任务详情传递好友bean;
    public static final String FRIEND_BEAN = "friend_bean";

    //微信提现--判断是否正在提现，用于跳转到提现结果页的标志
    public static final String USER_BALANCE_FLAG = "UserBalanceFlag";

    //没抢到红包，进入红包详情页使用（因为服务器返回的数据有误,没时间修改，前端可以处理）
    public static final String RED_PACKET_NOTHING = "red_packet_Nothing";


    /**
     * eventBus 传值
     */
    //提现申请成功后，返回-到兑换页面
    public static final int ONE   = 1;
    //提现申请成功后，返回 --到我的  页面
    public static final int TWO   = 2;
    //下载完成后，用于安装应用
    public static final int THREE = 3;
    //在正在进行中这个页面点击领取奖励，需要更新任务下载页的UI--TaskHomeFragment
    public static final int FOUR  = 4;
    //监听应用安装或者是替换
    public static final int FIVE  = 5;
    //点击taskAdapter中item开始下载时，用于修改底部UI
    public static final int SIX   = 6;

    /**
     * sharePerference 保存的数据
     */
    //用户ID
    public static final String USER_ID           = "user_id";
    //用户名
    public static final String USER_NAME         = "user_name";
    //用户头像
    public static final String USER_HEADURL      = "user_head_url";
    //用户手机号码
    public static final String USER_PHONE        = "user_phone";
    //状态
    public static final String USER_STATUS       = "user_status";
    //是否关注公众号
    public static final String USER_FOLLOW       = "user_follow";
    //设备号
    public static final String USER_DEVICENUMBER = "user_deviceNumber";
    //openid
    public static final String USER_OPENID       = "user_openid";
    //unionid
    public static final String USER_UNIONID      = "user_unionid";

    /**
     * 微信APPID
     */
    public static final String APP_ID = "wx6aa08d0d00404fe0";

    public static final String APP_SECRET = "cd36cfbe18d6202a6d37be334c16ecdf";


}
