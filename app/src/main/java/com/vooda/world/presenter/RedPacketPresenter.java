package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.RedPacketBean;
import com.vooda.world.model.IRedPacketModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.RedPacketModel;
import com.vooda.world.view.IRedPacketView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe   红包列表
 */

public class RedPacketPresenter  implements BasePresenter{


    private Context        mContext;
    private IRedPacketView  mIRedPacketView;
    private IRedPacketModel mIRedPacketModel;

    public RedPacketPresenter(Context context, IRedPacketView iRedPacketView) {
        mContext = context;
        mIRedPacketView = iRedPacketView;
        mIRedPacketModel = new RedPacketModel();
    }


    //获取收支明细
    public void getRedPacketDetail(int userID/*,int pageIndex*/) {
        mIRedPacketModel.getRedPacketDetail(userID/*,pageIndex*/, new OnBaseCallBack<RedPacketBean>() {
            @Override
            public void onSuccess(RedPacketBean RedPacketBean) {
                mIRedPacketView.setRedPacketDetailData(RedPacketBean);
            }

            @Override
            public void onFailed(String msg) {
                mIRedPacketView.networkFailed(msg);
            }
        });
    }

    @Override
    public void destroy() {
        mIRedPacketModel.destroy();
    }
}
