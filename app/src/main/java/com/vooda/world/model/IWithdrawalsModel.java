package com.vooda.world.model;

import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2017/1/3 15:20
 * Describe   提现
 */

public interface IWithdrawalsModel  extends IBaseModel{

    //获取余额提现的金额配置数据
    void getWebBalance(int userID, OnBaseCallBack<WithdrawalsBean> onBaseCallBack);

    //余额提现结果
    void getBalanceT(int userID,String amoney, OnBaseCallBack<WithdrawalsResultBean> onBaseCallBack);
}
