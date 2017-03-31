package com.vooda.world.view;

import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;

/**
 * Created by leijiaxq
 * Data       2016/12/30 18:48
 * Describe
 */

public interface IExchangeView extends IBaseView{

    //设置钱包详细数据
    void setExchangeDetailData(ExchangeBean bean);

    //设置用户是否关注微信公众号
    void setFollowWeixinNumber(FollowBean bean);
}
