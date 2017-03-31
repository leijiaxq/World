package com.vooda.world.bean;

import java.util.List;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:24
 * Describe    红包信息bean
 */
public class RedPacketBean {
    /**
     * Message : 红包记录查询成功
     * MyRedMoney : 1.3
     * RedCount : 160
     * RedMoney : 350.0
     * Result : ok
     * SurplusCount : 158
     * UserRedList : [{"URedDate":"2017-02-14 23:11:39.0","URedMoney":"1.3","HeadUrl":"ht","UserID":1,"UserName":"cyilin"},{"URedDate":"2017-02-14 23:08:33.0","URedMoney":"0.7","HeadUrl":"http://wx.qlogo.cn/mmopen/dePymVGyecviaeF5iaWs8wzgtIXsMDy8WKTAWZ1WibG1ibA6anKovWDNuVRdLQBATRsKdHYggDkm19TUOIR7DaVcnC7AjO1rDGfY/0","UserID":5,"UserName":"weyden"}]
     */

    private String Message;
    private String                  MyRedMoney;
    private String                  RedCount;
    private String                  RedMoney;
    private String                  Result;
    private String                  SurplusCount;
    private List<UserRedListEntity> UserRedList;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getMyRedMoney() {
        return MyRedMoney;
    }

    public void setMyRedMoney(String MyRedMoney) {
        this.MyRedMoney = MyRedMoney;
    }

    public String getRedCount() {
        return RedCount;
    }

    public void setRedCount(String RedCount) {
        this.RedCount = RedCount;
    }

    public String getRedMoney() {
        return RedMoney;
    }

    public void setRedMoney(String RedMoney) {
        this.RedMoney = RedMoney;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getSurplusCount() {
        return SurplusCount;
    }

    public void setSurplusCount(String SurplusCount) {
        this.SurplusCount = SurplusCount;
    }

    public List<UserRedListEntity> getUserRedList() {
        return UserRedList;
    }

    public void setUserRedList(List<UserRedListEntity> UserRedList) {
        this.UserRedList = UserRedList;
    }

    public static class UserRedListEntity {
        /**
         * URedDate : 2017-02-14 23:11:39.0
         * URedMoney : 1.3
         * HeadUrl : ht
         * UserID : 1
         * UserName : cyilin
         */

        private String URedDate;
        private String URedMoney;
        private String HeadUrl;
        private int    UserID;
        private String UserName;

        public String getURedDate() {
            return URedDate;
        }

        public void setURedDate(String URedDate) {
            this.URedDate = URedDate;
        }

        public String getURedMoney() {
            return URedMoney;
        }

        public void setURedMoney(String URedMoney) {
            this.URedMoney = URedMoney;
        }

        public String getHeadUrl() {
            return HeadUrl;
        }

        public void setHeadUrl(String HeadUrl) {
            this.HeadUrl = HeadUrl;
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
    }


    //    /**
//     * MyRedMoney : sample string 1
//     * RedCount : sample string 2
//     * RedMoney : sample string 3
//     * SurplusCount : sample string 4
//     * UserRedList : [{"URedMoney":"sample string 1","URedDate":"sample string 2","UserID":"sample string 3","UserName":"sample string 4","HeadUrl":"sample string 5"},{"URedMoney":"sample string 1","URedDate":"sample string 2","UserID":"sample string 3","UserName":"sample string 4","HeadUrl":"sample string 5"}]
//     * Result : sample string 5
//     * Message : sample string 6
//     */

//    private String                MyRedMoney;
//    private String                RedCount;
//    private String                RedMoney;
//    private String                SurplusCount;
//    private String                Result;
//    private String                Message;
//    /**
//     * URedMoney : sample string 1
//     * URedDate : sample string 2
//     * UserID : sample string 3
//     * UserName : sample string 4
//     * HeadUrl : sample string 5
//     */

    /*private List<UserRedListBean> UserRedList;

    public String getMyRedMoney() {
        return MyRedMoney;
    }

    public void setMyRedMoney(String MyRedMoney) {
        this.MyRedMoney = MyRedMoney;
    }

    public String getRedCount() {
        return RedCount;
    }

    public void setRedCount(String RedCount) {
        this.RedCount = RedCount;
    }

    public String getRedMoney() {
        return RedMoney;
    }

    public void setRedMoney(String RedMoney) {
        this.RedMoney = RedMoney;
    }

    public String getSurplusCount() {
        return SurplusCount;
    }

    public void setSurplusCount(String SurplusCount) {
        this.SurplusCount = SurplusCount;
    }

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

    public List<UserRedListBean> getUserRedList() {
        return UserRedList;
    }

    public void setUserRedList(List<UserRedListBean> UserRedList) {
        this.UserRedList = UserRedList;
    }

    public static class UserRedListBean {
        private String URedMoney;
        private String URedDate;
        private String UserID;
        private String UserName;
        private String HeadUrl;

        private boolean isLuckiest = false;  //是否是最幸运的人

        public boolean isLuckiest() {
            return isLuckiest;
        }

        public void setLuckiest(boolean luckiest) {
            isLuckiest = luckiest;
        }

        public String getURedMoney() {
            return URedMoney;
        }

        public void setURedMoney(String URedMoney) {
            this.URedMoney = URedMoney;
        }

        public String getURedDate() {
            return URedDate;
        }

        public void setURedDate(String URedDate) {
            this.URedDate = URedDate;
        }

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
    }*/
}
