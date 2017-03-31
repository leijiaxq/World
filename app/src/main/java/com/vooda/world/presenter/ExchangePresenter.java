package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.ExchangeBean;
import com.vooda.world.bean.FollowBean;
import com.vooda.world.model.IExchangeModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.ExchangeModel;
import com.vooda.world.view.IExchangeView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   兑换
 */

public class ExchangePresenter  implements BasePresenter{


    private Context        mContext;
    private IExchangeView  mIExchangeView;
    private IExchangeModel mIExchangeModel;

    public ExchangePresenter(Context context, IExchangeView iExchangeView) {
        mContext = context;
        mIExchangeView = iExchangeView;
        mIExchangeModel = new ExchangeModel();
    }


    //获取钱包详情数据
    public void getWalletDetail(int userID) {
        mIExchangeModel.getWalletDetail(userID, new OnBaseCallBack<ExchangeBean>() {
            @Override
            public void onSuccess(ExchangeBean bean) {
                mIExchangeView.setExchangeDetailData(bean);
            }

            @Override
            public void onFailed(String msg) {
                mIExchangeView.networkFailed(msg);
            }
        });
    }


    //获取用户是否关注微信公众号
    public void isFollowWeixinNumber(int userID) {
        mIExchangeModel.isFollowWeixinNumber(userID, new OnBaseCallBack<FollowBean>() {
            @Override
            public void onSuccess(FollowBean bean) {
                mIExchangeView.setFollowWeixinNumber(bean);
            }

            @Override
            public void onFailed(String msg) {
                mIExchangeView.networkFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {
        mIExchangeModel.destroy();
    }
}
