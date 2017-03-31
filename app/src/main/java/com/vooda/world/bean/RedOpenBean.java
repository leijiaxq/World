package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/1/3 10:47
 * Describe   打开红包的bean
 */

public class RedOpenBean {


    /**
     * URedMoney : sample string 1
     * URedDate : sample string 2
     * IsOpen : 3
     * Result : sample string 4
     * Message : sample string 5
     */

    private String URedMoney;
    private String URedDate;
    private int    IsOpen;
    private String Result;
    private String Message;

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

    public int getIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(int IsOpen) {
        this.IsOpen = IsOpen;
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
}
