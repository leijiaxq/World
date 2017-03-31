package com.vooda.world.bean;

import java.util.List;

/**
 * Created by leijiaxq
 * Data       2017/2/15 13:38
 * Describe   获取用户是否关注公众号
 */


public class FriendInfoBean {


    /**
     * IRDate : 2017-02-11 15:26:23
     * HeadUrl : http://wx.qlogo.cn/mmopen/dePymVGyecviaeF5iaWs8wzgtIXsMDy8WKTAWZ1WibG1ibA6anKovWDNuVRdLQBATRsKdHYggDkm19TUOIR7DaVcnC7AjO1rDGfY/0
     * Message : 获取成功
     * Result : ok
     * TaskList : [{"GetBonus":"0.24","IsPay4":1,"ReceiveDate":"2017-02-15 17:47:51","TaskID":1,"TaskTitle":"QQ"},{"GetBonus":"1.2","IsPay4":1,"ReceiveDate":"2017-02-15 17:46:49","TaskID":2,"TaskTitle":"网易云音乐1"}]
     * UserName : weyden
     */

    private String               IRDate;
    private String               HeadUrl;
    private String               Message;
    private String               Result;
    private String               UserName;
    private List<TaskListEntity> TaskList;

    public String getIRDate() {
        return IRDate;
    }

    public void setIRDate(String IRDate) {
        this.IRDate = IRDate;
    }

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String HeadUrl) {
        this.HeadUrl = HeadUrl;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public List<TaskListEntity> getTaskList() {
        return TaskList;
    }

    public void setTaskList(List<TaskListEntity> TaskList) {
        this.TaskList = TaskList;
    }

    public static class TaskListEntity {
        /**
         * GetBonus : 0.24
         * IsPay4 : 1
         * ReceiveDate : 2017-02-15 17:47:51
         * TaskID : 1
         * TaskTitle : QQ
         */

        private String GetBonus;
        private int    IsPay4;
        private String ReceiveDate;
        private int    TaskID;
        private String TaskTitle;

        public String getGetBonus() {
            return GetBonus;
        }

        public void setGetBonus(String GetBonus) {
            this.GetBonus = GetBonus;
        }

        public int getIsPay4() {
            return IsPay4;
        }

        public void setIsPay4(int IsPay4) {
            this.IsPay4 = IsPay4;
        }

        public String getReceiveDate() {
            return ReceiveDate;
        }

        public void setReceiveDate(String ReceiveDate) {
            this.ReceiveDate = ReceiveDate;
        }

        public int getTaskID() {
            return TaskID;
        }

        public void setTaskID(int TaskID) {
            this.TaskID = TaskID;
        }

        public String getTaskTitle() {
            return TaskTitle;
        }

        public void setTaskTitle(String TaskTitle) {
            this.TaskTitle = TaskTitle;
        }
    }
}
