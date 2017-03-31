package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/1/3 16:00
 * Describe   提现结果bean
 */

public class WithdrawalsResultBean {


    /**
     * UserBalance : sample string 1
     * CanBalance : sample string 2
     * Result : sample string 3
     * Message : sample string 4
     */

    private String UserBalance;
    private String CanBalance;
    private String Result;
    private String Message;

    public String getUserBalance() {
        return UserBalance;
    }

    public void setUserBalance(String UserBalance) {
        this.UserBalance = UserBalance;
    }

    public String getCanBalance() {
        return CanBalance;
    }

    public void setCanBalance(String CanBalance) {
        this.CanBalance = CanBalance;
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
