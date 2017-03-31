package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/2/15 13:38
 * Describe   获取用户是否关注公众号
 */

public class FollowBean {


    /**
     * Result : ok
     * Message : 获取用户是否关注公众号
     * IsFollow : 1
     */

    private String Result;
    private String Message;
    private int    IsFollow;

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

    public int getIsFollow() {
        return IsFollow;
    }

    public void setIsFollow(int IsFollow) {
        this.IsFollow = IsFollow;
    }
}
