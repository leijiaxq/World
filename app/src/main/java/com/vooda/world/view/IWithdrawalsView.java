package com.vooda.world.view;

import com.vooda.world.bean.WithdrawalsBean;
import com.vooda.world.bean.WithdrawalsResultBean;

/**
 * Created by leijiaxq
 * Data       2017/1/3 15:20
 * Describe   提现
 */

public interface IWithdrawalsView extends IBaseView{

    //设置金额配置的数据
    void setWebBalanceData(WithdrawalsBean withdrawalsBean);

    //设置余额提现结果成功
    void setBalanceTResult(WithdrawalsResultBean bean);

    //设置余额提现结果失败
    void setBalanceTResultFail(String msg);
}
