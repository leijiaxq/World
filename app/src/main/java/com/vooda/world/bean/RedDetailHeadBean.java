package com.vooda.world.bean;

/**
 * Created by leijiaxq
 * Data       2017/1/3 11:32
 * Describe   红包详情页头布局的bean对象
 */

public class RedDetailHeadBean {


    /**
     * MyRedMoney : 1.00
     * RedCount : 2
     * RedMoney : 996.66
     * SurplusCount : 1
     */

    private String MyRedMoney;//我的红包金额
    private String RedCount;//红包个数
    private String RedMoney;//红包总金额
    private String SurplusCount;  //红包剩余个数

    public String getMyRedMoney() {
        return MyRedMoney;
    }

    public void setMyRedMoney(String MyRedMoney) {
        this.MyRedMoney = MyRedMoney;
    }

    public String getRedCount() {
        return RedCount;
    }

    public void setRedCount(String RedCount) {
        this.RedCount = RedCount;
    }

    public String getRedMoney() {
        return RedMoney;
    }

    public void setRedMoney(String RedMoney) {
        this.RedMoney = RedMoney;
    }

    public String getSurplusCount() {
        return SurplusCount;
    }

    public void setSurplusCount(String SurplusCount) {
        this.SurplusCount = SurplusCount;
    }
}
