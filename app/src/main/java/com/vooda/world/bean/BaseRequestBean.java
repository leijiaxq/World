package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2016/12/28 12:47
 * Describe
 */


public class BaseRequestBean {
    protected String Result;
    protected String Message;

    public BaseRequestBean() {}

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "BaseRequestBean{" +
                "Result='" + Result + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
