package com.vooda.world.model;

import com.vooda.world.bean.FriendInfoBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/28 20:43
 * Describe
 */

public interface IMyFriendInfoModel  extends IBaseModel{
   void getFriendInfoList(int userID,int FriendID,int pageIndex,OnBaseCallBack<FriendInfoBean> onBaseCallBack);
}
