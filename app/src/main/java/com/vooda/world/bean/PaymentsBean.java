package com.vooda.world.bean;

import java.util.List;

/**
 * Created by leijiaxq
 * Data       2016/12/28 11:56
 * Describe   收支详情bean
 */

public class PaymentsBean {
    /**
     * BalancePayList : [{"CType":"sample string 1","BClassType":"sample string 2","BCName":"sample string 3","CMoney":"sample string 4","ChangeDate":"sample string 5"},{"CType":"sample string 1","BClassType":"sample string 2","BCName":"sample string 3","CMoney":"sample string 4","ChangeDate":"sample string 5"}]
     * Result : sample string 1
     * Message : sample string 2
     */

    private String Result;
    private String Message;
    /**
     * CType : sample string 1
     * BClassType : sample string 2
     * BCName : sample string 3
     * CMoney : sample string 4
     * ChangeDate : sample string 5
     */

    private List<BalancePayList> BalancePayList;

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

    public List<BalancePayList> getBalancePayList() {
        return BalancePayList;
    }

    public void setBalancePayList(List<BalancePayList> BalancePayList) {
        this.BalancePayList = BalancePayList;
    }

    public static class BalancePayList {

        private String DateFlag;  //日期标志         最近/七天前/更早
        private boolean IsShowLine = true;  //是否显示线条

        private int    CType;//                          int		异动类型(1支出2收入)	编辑
        private int    BClassType;//                        	int		资金类型(0 默认1任务收入 2红包收入 3 分销收入)	编辑
        private String BCName;//                           String		异动说明	编辑
        private String CMoney;//                   String		异动金额	编辑
        private String ChangeDate;//               String		异动时间

        public String getDateFlag() {
            return DateFlag;
        }

        public void setDateFlag(String dateFlag) {
            DateFlag = dateFlag;
        }

        public boolean isShowLine() {
            return IsShowLine;
        }

        public void setShowLine(boolean showLine) {
            IsShowLine = showLine;
        }

        public int getCType() {
            return CType;
        }

        public void setCType(int CType) {
            this.CType = CType;
        }

        public int getBClassType() {
            return BClassType;
        }

        public void setBClassType(int BClassType) {
            this.BClassType = BClassType;
        }

        public String getBCName() {
            return BCName;
        }

        public void setBCName(String BCName) {
            this.BCName = BCName;
        }

        public String getCMoney() {
            return CMoney;
        }

        public void setCMoney(String CMoney) {
            this.CMoney = CMoney;
        }

        public String getChangeDate() {
            return ChangeDate;
        }

        public void setChangeDate(String changeDate) {
            ChangeDate = changeDate;
        }
    }
}
