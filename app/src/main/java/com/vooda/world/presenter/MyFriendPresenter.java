package com.vooda.world.presenter;

import android.content.Context;

import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.model.IMyFriendModel;
import com.vooda.world.model.callback.OnBaseCallBack;
import com.vooda.world.model.impl.MyFriendModel;
import com.vooda.world.view.IMyFriendView;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:38
 * Describe
 */

public class MyFriendPresenter  implements BasePresenter{


    private Context        mContext;
    private IMyFriendView  mIMyFriendView;
    private IMyFriendModel mIMyFriendModel;

    public MyFriendPresenter(Context context, IMyFriendView iMyFriendView) {
        mContext = context;
        mIMyFriendView = iMyFriendView;
        mIMyFriendModel = new MyFriendModel();
    }


    //获取好友列表
    public void getFriendList(int  userID,int pageIndex) {
        mIMyFriendModel.getFriendList(userID,pageIndex, new OnBaseCallBack<MyFriendBean>() {
            @Override
            public void onSuccess(MyFriendBean myFriendBean) {
                mIMyFriendView.setFriendData(myFriendBean);
            }

            @Override
            public void onFailed(String msg) {
                mIMyFriendView.networkFailed(msg);
            }
        });
    }


    @Override
    public void destroy() {
        mIMyFriendModel.destroy();
    }
}
