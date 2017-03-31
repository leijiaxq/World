package com.vooda.world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:56
 * Describe   我的好友bean
 */

public class MyFriendBean {
    /**
     * FriendList : [{"HeadUrl":"http://wx.qlogo.cn/mmopen/WAK1NicFdzVftQSzZnia5kymevx2jD490CSweibLj3rkZWXHvXWMB8gSsyEyaAuKDrg1TdcHc5PaBlvZMFmFpBIp7Zev7C6I0RS/0","IRDate":"2017-02-13 20:07:29.0","IsSuccess":1,"UserID":6,"UserName":"九浅伊深"},{"HeadUrl":"http://wx.qlogo.cn/mmopen/dePymVGyecviaeF5iaWs8wzgtIXsMDy8WKTAWZ1WibG1ibA6anKovWDNuVRdLQBATRsKdHYggDkm19TUOIR7DaVcnC7AjO1rDGfY/0","IRDate":"2017-02-11 15:26:23.0","IsSuccess":1,"UserID":5,"UserName":"weyden"}]
     * Message : 我的好友列表
     * Result : ok
     */

    private String                 Message;
    private String                 Result;
    private List<FriendListEntity> FriendList;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public List<FriendListEntity> getFriendList() {
        return FriendList;
    }

    public void setFriendList(List<FriendListEntity> FriendList) {
        this.FriendList = FriendList;
    }

    public static class FriendListEntity implements Parcelable {
        /**
         * HeadUrl : http://wx.qlogo.cn/mmopen/WAK1NicFdzVftQSzZnia5kymevx2jD490CSweibLj3rkZWXHvXWMB8gSsyEyaAuKDrg1TdcHc5PaBlvZMFmFpBIp7Zev7C6I0RS/0
         * IRDate : 2017-02-13 20:07:29.0
         * IsSuccess : 1
         * UserID : 6
         * UserName : 九浅伊深
         */

        private String HeadUrl;
        private String IRDate;
        private int    IsSuccess;
        private int    UserID;
        private String UserName;

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
        }

        public String getIRDate() {
            return IRDate;
        }

        public void setIRDate(String IRDate) {
            this.IRDate = IRDate;
        }

        public int getIsSuccess() {
            return IsSuccess;
        }

        public void setIsSuccess(int IsSuccess) {
            this.IsSuccess = IsSuccess;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.HeadUrl);
            dest.writeString(this.IRDate);
            dest.writeInt(this.IsSuccess);
            dest.writeInt(this.UserID);
            dest.writeString(this.UserName);
        }

        public FriendListEntity() {
        }

        protected FriendListEntity(Parcel in) {
            this.HeadUrl = in.readString();
            this.IRDate = in.readString();
            this.IsSuccess = in.readInt();
            this.UserID = in.readInt();
            this.UserName = in.readString();
        }

        public static final Parcelable.Creator<FriendListEntity> CREATOR = new Parcelable.Creator<FriendListEntity>() {
            @Override
            public FriendListEntity createFromParcel(Parcel source) {
                return new FriendListEntity(source);
            }

            @Override
            public FriendListEntity[] newArray(int size) {
                return new FriendListEntity[size];
            }
        };
    }


    //    /**
    //     * FriendList : [{"UserID":"sample string 1","UserName":"sample string 2","HeadUrl":"sample string 3","IRDate":"sample string 4","IsSuccess":"sample string 5"},{"UserID":"sample string 1","UserName":"sample string 2","HeadUrl":"sample string 3","IRDate":"sample string 4","IsSuccess":"sample string 5"}]
    //     * Result : sample string 1
    //     * Message : sample string 2
    //     */
    //
    //    private String               Result;
    //    private String               Message;
    //    /**
    //     * UserID : sample string 1
    //     * UserName : sample string 2
    //     * HeadUrl : sample string 3
    //     * IRDate : sample string 4
    //     * IsSuccess : sample string 5
    //     */

    /*private List<FriendListBean> FriendList;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<FriendListBean> getFriendList() {
        return FriendList;
    }

    public void setFriendList(List<FriendListBean> FriendList) {
        this.FriendList = FriendList;
    }

    public static class FriendListBean {
        private String UserID;
        private String UserName;
        private String HeadUrl;
        private String IRDate;
        private String IsSuccess;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
        }

        public String getIRDate() {
            return IRDate;
        }

        public void setIRDate(String IRDate) {
            this.IRDate = IRDate;
        }

        public String getIsSuccess() {
            return IsSuccess;
        }

        public void setIsSuccess(String IsSuccess) {
            this.IsSuccess = IsSuccess;
        }
    }*/
}
