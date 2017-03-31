package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/1/3 15:23
 * Describe   余额提现金额配置bean
 */

public class WithdrawalsBean {


    /**
     * ValueUrl : sample string 1
     * Result : sample string 2
     * Message : sample string 3
     */

    private String ValueUrl;
    private String Result;
    private String Message;

    public String getValueUrl() {
        return ValueUrl;
    }

    public void setValueUrl(String ValueUrl) {
        this.ValueUrl = ValueUrl;
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
