package com.vooda.world.view;

import com.vooda.world.bean.PaymentsBean;

/**
 * Created by leijiaxq
 * Data       2016/12/29 17:26
 * Describe
 */

public interface IPaymentsView extends IBaseView{

//    设置收支详情数据
    void setPaymentsDetailData(PaymentsBean paymentsBean);
}
