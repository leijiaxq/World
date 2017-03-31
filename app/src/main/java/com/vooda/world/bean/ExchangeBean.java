package com.vooda.world.bean;

import java.util.List;

/**
 * Created by leijiaxq
 * Data       2016/12/30 18:50
 * Describe   钱包bean
 */

public class ExchangeBean {
    /**
     * Amoney : 0
     * IsFinish : 0
     * LastDate : 2017-02-27 13:44:38
     * Message : 用户钱包余额
     * Result : ok
     * SonList : [{"sonsCount":0,"sonsEarn":0,"sonsLevel":1,"sonsTaskCount":0},{"sonsCount":0,"sonsEarn":0,"sonsLevel":2,"sonsTaskCount":0},{"sonsCount":0,"sonsEarn":0,"sonsLevel":3,"sonsTaskCount":0},{"sonsCount":0,"sonsEarn":0,"sonsLevel":4,"sonsTaskCount":0}]
     * UserBalance : 6.25
     * UserCanBalance : 6.25
     * UserSumBalance : 6.25
     */

    private String              Amoney;
    private int                 IsFinish;
    private String              LastDate;
    private String              Message;
    private String              Result;
    private String              UserBalance;
    private String              UserCanBalance;
    private String              UserSumBalance;
    private List<SonListEntity> SonList;

    public String getAmoney() {
        return Amoney;
    }

    public void setAmoney(String Amoney) {
        this.Amoney = Amoney;
    }

    public int getIsFinish() {
        return IsFinish;
    }

    public void setIsFinish(int IsFinish) {
        this.IsFinish = IsFinish;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String LastDate) {
        this.LastDate = LastDate;
    }

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

    public String getUserBalance() {
        return UserBalance;
    }

    public void setUserBalance(String UserBalance) {
        this.UserBalance = UserBalance;
    }

    public String getUserCanBalance() {
        return UserCanBalance;
    }

    public void setUserCanBalance(String UserCanBalance) {
        this.UserCanBalance = UserCanBalance;
    }

    public String getUserSumBalance() {
        return UserSumBalance;
    }

    public void setUserSumBalance(String UserSumBalance) {
        this.UserSumBalance = UserSumBalance;
    }

    public List<SonListEntity> getSonList() {
        return SonList;
    }

    public void setSonList(List<SonListEntity> SonList) {
        this.SonList = SonList;
    }

    public static class SonListEntity {
        /**
         * sonsCount : 0
         * sonsEarn : 0
         * sonsLevel : 1
         * sonsTaskCount : 0
         */

        private int   sonsCount;
        private float sonsEarn;
        private int   sonsLevel;
        private int   sonsTaskCount;

        public int getSonsCount() {
            return sonsCount;
        }

        public void setSonsCount(int sonsCount) {
            this.sonsCount = sonsCount;
        }

        public float getSonsEarn() {
            return sonsEarn;
        }

        public void setSonsEarn(float sonsEarn) {
            this.sonsEarn = sonsEarn;
        }

        public int getSonsLevel() {
            return sonsLevel;
        }

        public void setSonsLevel(int sonsLevel) {
            this.sonsLevel = sonsLevel;
        }

        public int getSonsTaskCount() {
            return sonsTaskCount;
        }

        public void setSonsTaskCount(int sonsTaskCount) {
            this.sonsTaskCount = sonsTaskCount;
        }
    }


    //    /**
    //     * Amoney : 9.46
    //     * IsFinish : 1
    //     * LastDate : 2017-02-14 23:11:39.0
    //     * Message : 用户钱包余额
    //     * Result : ok
    //     * UserBalance : 32.96
    //     * UserCanBalance : 23.5
    //     * UserSumBalance : 33.29
    //     */
    //
    //    private String Amoney;
    //    private int    IsFinish;
    //    private String LastDate;
    //    private String Message;
    //    private String Result;
    //    private String UserBalance;
    //    private String UserCanBalance;
    //    private String UserSumBalance;
    //
    //    public String getAmoney() {
    //        return Amoney;
    //    }
    //
    //    public void setAmoney(String Amoney) {
    //        this.Amoney = Amoney;
    //    }
    //
    //    public int getIsFinish() {
    //        return IsFinish;
    //    }
    //
    //    public void setIsFinish(int IsFinish) {
    //        this.IsFinish = IsFinish;
    //    }
    //
    //    public String getLastDate() {
    //        return LastDate;
    //    }
    //
    //    public void setLastDate(String LastDate) {
    //        this.LastDate = LastDate;
    //    }
    //
    //    public String getMessage() {
    //        return Message;
    //    }
    //
    //    public void setMessage(String Message) {
    //        this.Message = Message;
    //    }
    //
    //    public String getResult() {
    //        return Result;
    //    }
    //
    //    public void setResult(String Result) {
    //        this.Result = Result;
    //    }
    //
    //    public String getUserBalance() {
    //        return UserBalance;
    //    }
    //
    //    public void setUserBalance(String UserBalance) {
    //        this.UserBalance = UserBalance;
    //    }
    //
    //    public String getUserCanBalance() {
    //        return UserCanBalance;
    //    }
    //
    //    public void setUserCanBalance(String UserCanBalance) {
    //        this.UserCanBalance = UserCanBalance;
    //    }
    //
    //    public String getUserSumBalance() {
    //        return UserSumBalance;
    //    }
    //
    //    public void setUserSumBalance(String UserSumBalance) {
    //        this.UserSumBalance = UserSumBalance;
    //    }


    //    /**
    //     * UserBalance : sample string 1
    //     * UserSumBalance : sample string 2
    //     * UserCanBalance : sample string 3
    //     * LastDate : sample string 4
    //     * Isfinish : 5
    //     * Amoney : sample string 6
    //     * Result : sample string 7
    //     * Message : sample string 8
    //     */

   /* private String UserBalance;
    private String UserSumBalance;
    private String UserCanBalance;
    private String LastDate;
    private int    Isfinish;
    private String Amoney;
    private String Result;
    private String Message;

    public String getUserBalance() {
        return UserBalance;
    }

    public void setUserBalance(String UserBalance) {
        this.UserBalance = UserBalance;
    }

    public String getUserSumBalance() {
        return UserSumBalance;
    }

    public void setUserSumBalance(String UserSumBalance) {
        this.UserSumBalance = UserSumBalance;
    }

    public String getUserCanBalance() {
        return UserCanBalance;
    }

    public void setUserCanBalance(String UserCanBalance) {
        this.UserCanBalance = UserCanBalance;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String LastDate) {
        this.LastDate = LastDate;
    }

    public int getIsfinish() {
        return Isfinish;
    }

    public void setIsfinish(int Isfinish) {
        this.Isfinish = Isfinish;
    }

    public String getAmoney() {
        return Amoney;
    }

    public void setAmoney(String Amoney) {
        this.Amoney = Amoney;
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
    }*/
}
