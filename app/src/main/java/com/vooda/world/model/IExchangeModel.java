package com.vooda.world.model;

import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/30 18:48
 * Describe
 */

public interface IExchangeModel extends IBaseModel{

    //获取钱包详细数据
    void getWalletDetail(int userID, OnBaseCallBack<ExchangeBean> onBaseCallBack);

    //获取用户是否关注微信公众号
    void isFollowWeixinNumber(int userID, OnBaseCallBack<FollowBean> onBaseCallBack);
}
