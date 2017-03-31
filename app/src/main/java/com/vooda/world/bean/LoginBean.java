package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2016/12/29 10:07
 * Describe   第三方登录返回的数据
 */

public class LoginBean {
    /**
     * Isfollow : 1
     * LastLoginDate : 2017-02-15 01:37:24.0
     * Message : 用户微信登录接口
     * Phone :
     * RegisterDate : 2017-02-11 14:54:32.0
     * Result : ok
     * UserID : 1
     */

    private int Isfollow;
    private String LastLoginDate;
    private String Message;
    private String Phone;
    private String RegisterDate;
    private String Result;
    private String UserID;

    public int getIsfollow() {
        return Isfollow;
    }

    public void setIsfollow(int isfollow) {
        Isfollow = isfollow;
    }

    public String getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        LastLoginDate = lastLoginDate;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    //    /**
    //     * UserID : 3
    //     * UserName : 一路向前
    //     * HeadUrl : http://wx.qlogo.cn/mmopen/tEyibl9iaE4WOnEArDJicCL1nHzL9E0Gzhqkc3yO0EeoQa8W2snD4nNia6I5Kymc1NlLMpZPXubficumPOY8pxHN20yAxcocxH0QD/0
    //     * Phone : null
    //     * RegisterDate : 2017-1-11 10:35:42
    //     * LastLoginDate : 2017-1-11 10:35:42
    //     * IsStatus : 1
    //     * OpenID : oze6jwVryncek4u8xtg1TA944Weo
    //     * DeviceNumber : fe367093aaaebd3c
    //     * Isfollow : 0
    //     * Result : ok
    //     * Message : 登录成功
    //     */

    /*private int UserID;
    private String UserName;
    private String HeadUrl;
    private String Phone;
    private String RegisterDate;
    private String LastLoginDate;
    private int    IsStatus;
    private String OpenID;
    private String DeviceNumber;
    private int    Isfollow;
    private String Result;
    private String Message;

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

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String HeadUrl) {
        this.HeadUrl = HeadUrl;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public void setRegisterDate(String RegisterDate) {
        this.RegisterDate = RegisterDate;
    }

    public String getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(String LastLoginDate) {
        this.LastLoginDate = LastLoginDate;
    }

    public int getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(int IsStatus) {
        this.IsStatus = IsStatus;
    }

    public String getOpenID() {
        return OpenID;
    }

    public void setOpenID(String OpenID) {
        this.OpenID = OpenID;
    }

    public String getDeviceNumber() {
        return DeviceNumber;
    }

    public void setDeviceNumber(String DeviceNumber) {
        this.DeviceNumber = DeviceNumber;
    }

    public int getIsfollow() {
        return Isfollow;
    }

    public void setIsfollow(int Isfollow) {
        this.Isfollow = Isfollow;
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
