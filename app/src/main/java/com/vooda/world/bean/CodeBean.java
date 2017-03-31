package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/1/17 13:38
 * Describe   生成二维码返回的bean对象
 */

public class CodeBean {


    /**
     * Message : 生成成功
     * Result : ok
     * code : gQFR8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyeDhiSTVkSDFkZjAxMDAwMHcwM24AAgThgn1YAwQAAAAA
     */

    private String Message;
    private String Result;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
