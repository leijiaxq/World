package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.FriendInfoBean;
import com.vooda.world.model.IMyFriendInfoModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.MyFriendInfoModel;
import com.vooda.world.view.IMyFriendInfoView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe
 */

public class MyFriendInfoPresenter  implements BasePresenter{


    private Context            mContext;
    private IMyFriendInfoView  mIMyFriendInfoView;
    private IMyFriendInfoModel mIMyFriendInfoModel;

    public MyFriendInfoPresenter(Context context, IMyFriendInfoView iMyFriendInfoView) {
        mContext = context;
        mIMyFriendInfoView = iMyFriendInfoView;
        mIMyFriendInfoModel = new MyFriendInfoModel();
    }


    //获取好友列表
    public void getFriendInfoList(int userID,int FriendID, int pageIndex) {
        mIMyFriendInfoModel.getFriendInfoList(userID,FriendID, pageIndex, new OnBaseCallBack<FriendInfoBean>() {
            @Override
            public void onSuccess(FriendInfoBean bean) {
//                LogUtil.d(TAG, "onSuccess---" + Thread.currentThread().getId());

                mIMyFriendInfoView.setFriendInfoData(bean);
            }

            @Override
            public void onFailed(String msg) {
//                LogUtil.d(TAG, "onSuccess---" + Thread.currentThread().getId());
                mIMyFriendInfoView.networkFailed(msg);
            }
        });
    }


    @Override
    public void destroy() {
        mIMyFriendInfoModel.destroy();
    }
}
