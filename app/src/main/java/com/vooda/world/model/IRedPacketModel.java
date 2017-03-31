package com.vooda.world.model;

import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by  leijiaxq
 * Data        2016/12/31 13:10
 * Describe
 */
public interface IRedPacketModel  extends IBaseModel{

    //获取红包列表数据
    void getRedPacketDetail(int userID/*, int pageIndex*/, OnBaseCallBack<RedPacketBean> onBaseCallBack);
}
