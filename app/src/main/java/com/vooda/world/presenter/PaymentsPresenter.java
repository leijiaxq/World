package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.PaymentsBean;
import com.vooda.world.model.IPaymentsModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.PaymentsModel;
import com.vooda.world.view.IPaymentsView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   收支明细
 */

public class PaymentsPresenter  implements BasePresenter{


    private Context        mContext;
    private IPaymentsView  mIPaymentsView;
    private IPaymentsModel mIPaymentsModel;

    public PaymentsPresenter(Context context, IPaymentsView iPaymentsView) {
        mContext = context;
        mIPaymentsView = iPaymentsView;
        mIPaymentsModel = new PaymentsModel();
    }


    //获取收支明细
    public void getPaymentsDetail(int userID,int pageIndex) {
        mIPaymentsModel.getPaymentsDetail(userID,pageIndex, new OnBaseCallBack<PaymentsBean>() {
            @Override
            public void onSuccess(PaymentsBean paymentsBean) {
                mIPaymentsView.setPaymentsDetailData(paymentsBean);
            }

            @Override
            public void onFailed(String msg) {
                mIPaymentsView.networkFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {
        mIPaymentsModel.destroy();
    }
}
