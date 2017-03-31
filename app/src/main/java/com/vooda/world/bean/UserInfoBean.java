package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:24
 * Describe    用户信息bean
 */

public class UserInfoBean {

    public int UserID = 0;//                               int		用户ID	编辑
    public String UserName;//                         String		用户名	编辑
    public String HeadUrl;//                String		头像	编辑
    public String Phone;//                              String		手机号	编辑
    public String RegisterDate;//                                 String		注册时间	编辑
    public int    IsStatus;//                     int		状态(0不显示1显示)	编辑
    public int Isfollow = 0;//                     int		是否关注公众号(0否1是)	编辑
    public String DeviceNumber;//                            	String		设备号	编辑
    public String OpenID;//                   String		第三方登录凭证
    public String UnionID;

    public String getUnionID() {
        return UnionID;
    }

    public void setUnionID(String unionID) {
        UnionID = unionID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String headUrl) {
        HeadUrl = headUrl;
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

    public int getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(int isStatus) {
        IsStatus = isStatus;
    }

    public int getIsfollow() {
        return Isfollow;
    }

    public void setIsfollow(int isfollow) {
        Isfollow = isfollow;
    }

    public String getDeviceNumber() {
        return DeviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        DeviceNumber = deviceNumber;
    }

    public String getOpenID() {
        return OpenID;
    }

    public void setOpenID(String openID) {
        OpenID = openID;
    }

    private int userID = 0;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
