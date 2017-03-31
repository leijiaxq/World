package com.vooda.world.model;

import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/29 17:26
 * Describe
 */

public interface IPaymentsModel  extends IBaseModel{
    void getPaymentsDetail(int userID, int pageIndex,OnBaseCallBack<PaymentsBean> onBaseCallBack);
}
