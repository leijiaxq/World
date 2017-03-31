package com.vooda.world.model;

import com.vooda.world.bean.MyFriendBean;
import com.vooda.world.model.callback.OnBaseCallBack;

/**
 * Created by leijiaxq
 * Data       2016/12/28 12:40
 * Describe
 */

public interface IMyFriendModel  extends IBaseModel{

    void getFriendList(int userID,int pageIndex,OnBaseCallBack<MyFriendBean> onBaseCallBack);
}
