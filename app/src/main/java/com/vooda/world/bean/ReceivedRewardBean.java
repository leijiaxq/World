package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/2/15 11:56
 * Describe   领取奖励返回的bean
 */

public class ReceivedRewardBean {


    /**
     * PayMoney : ”1.0”
     * Message : 任务完成，奖励已发放
     * Result : ok
     */

    private String PayMoney;
    private String Message;
    private String Result;
    private int IsPay4;

    public int getIsPay4() {
        return IsPay4;
    }

    public void setIsPay4(int isPay4) {
        this.IsPay4 = isPay4;
    }

    public String getPayMoney() {
        return PayMoney;
    }

    public void setPayMoney(String PayMoney) {
        this.PayMoney = PayMoney;
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
}
