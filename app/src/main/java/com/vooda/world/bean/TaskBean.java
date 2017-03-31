package com.vooda.world.bean;

import java.util.List;

import zlc.season.rxdownload.entity.DownloadRecord;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:24
 * Describe
 */
public class TaskBean {

    /**
     * UserTaskList : [{"UserID":1,"IsFinish":1,"ReceiveDate":"2016-12-24T00:00:00","TaskIconUrl":"www.kkk.com","TaskTitle":"特种任务","TaskContent":"这个游戏真的很好玩的咯","TaskUrl":"www.baidu.com","TaskBonus":"5.00","TaskPrompt":"下载完成必须打开","AppPackage":"yuyhh","IsOpen":0},{"UserID":1,"IsFinish":1,"ReceiveDate":"2016-12-24T00:00:00","TaskIconUrl":"www.kkk.com","TaskTitle":"特种任务","TaskContent":"这个游戏真的很好玩的咯","TaskUrl":"www.baidu.com","TaskBonus":"5.00","TaskPrompt":"下载完成必须打开","AppPackage":"yuyhh","IsOpen":0},{"UserID":1,"IsFinish":1,"ReceiveDate":"2016-12-24T00:00:00","TaskIconUrl":"www.kkk.com","TaskTitle":"特种任务","TaskContent":"这个游戏真的很好玩的咯","TaskUrl":"www.baidu.com","TaskBonus":"5.00","TaskPrompt":"下载完成必须打开","AppPackage":"yuyhh","IsOpen":0},{"UserID":1,"IsFinish":1,"ReceiveDate":"2016-12-24T00:00:00","TaskIconUrl":"www.kkk.com","TaskTitle":"特种任务","TaskContent":"这个游戏真的很好玩的咯","TaskUrl":"www.baidu.com","TaskBonus":"5.00","TaskPrompt":"下载完成必须打开","AppPackage":"yuyhh","IsOpen":0}]
     * Result : ok
     * Message : 获取成功
     */

    private String             Result;
    private String             Message;
    /**
     * UserID : 1
     * IsFinish : 1
     * ReceiveDate : 2016-12-24T00:00:00
     * TaskIconUrl : www.kkk.com
     * TaskTitle : 特种任务
     * TaskContent : 这个游戏真的很好玩的咯
     * TaskUrl : www.baidu.com
     * TaskBonus : 5.00
     * TaskPrompt : 下载完成必须打开
     * AppPackage : yuyhh
     * IsOpen : 0
     */

    private List<UserTaskList> UserTaskList;

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

    public List<UserTaskList> getUserTaskList() {
        return UserTaskList;
    }

    public void setUserTaskList(List<UserTaskList> UserTaskList) {
        this.UserTaskList = UserTaskList;
    }

    public static class UserTaskList {

       /* AppPackeage : "com.netease.cloudmusic",
        IsDownload: 0,
        IsInstall: 0,
        IsOpen: 0,
        IsPay4: 0,
        ReceiveDate: "",
        TaskBonus: "1.0",
        TaskContent: "网易云音乐",
        TaskID: 8,
        TaskIconUrl: "",
        TaskTitle: "网易云音乐",
        TaskPrompt: "",
        TaskUrl: "http://s1.music.126.net/download/android/CloudMusic_official_3.7.3_15	3912.apk"*/

        private String AppPackeage;//                : "com.netease.cloudmusic",
        private int    IsDownload;//                       : 0,
        private int    IsInstall;//                               : 0,
        private int    IsOpen;//                           : 0,
        private int    IsPay4;//                           : 0,
        private String ReceiveDate;//                           : "",
        private String TaskBonus;//                   : "1.0",
        private String TaskContent;//               : "网易云音乐",
        private int    TaskID;//               : 8,
        private String TaskIconUrl;//                       : "",
        private String TaskTitle;//                   : "网易云音乐",
        private String TaskPrompt;//                   : "",
        private String TaskUrl;//                   : "http://s1.music.126.net/download/android/CloudMusic_official_3.7.3_15	3912.apk
        private int    TaskSurplusCount;//                 任务剩余个数


        private boolean IsStart = false;   //是否正在进行任务
        private DownloadRecord mRecord;
        private String         saveName;

        //正在进行中的任务和已完成的任务  需要的flag
        private String TimeFlag;
        //是否显示线条
        private boolean isShowLine = true;

        public int getTaskSurplusCount() {
            return TaskSurplusCount;
        }

        public void setTaskSurplusCount(int taskSurplusCount) {
            TaskSurplusCount = taskSurplusCount;
        }

        public String getTimeFlag() {
            return TimeFlag;
        }

        public void setTimeFlag(String timeFlag) {
            TimeFlag = timeFlag;
        }

        public boolean isShowLine() {
            return isShowLine;
        }

        public void setShowLine(boolean showLine) {
            isShowLine = showLine;
        }

        public boolean isStart() {
            return IsStart;
        }

        public void setStart(boolean start) {
            IsStart = start;
        }

        public DownloadRecord getRecord() {
            return mRecord;
        }

        public void setRecord(DownloadRecord record) {
            mRecord = record;
        }

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        public String getAppPackeage() {
            return AppPackeage;
        }

        public void setAppPackeage(String appPackeage) {
            AppPackeage = appPackeage;
        }

        public int getIsDownload() {
            return IsDownload;
        }

        public void setIsDownload(int isDownload) {
            IsDownload = isDownload;
        }

        public int getIsInstall() {
            return IsInstall;
        }

        public void setIsInstall(int isInstall) {
            IsInstall = isInstall;
        }

        public int getIsOpen() {
            return IsOpen;
        }

        public void setIsOpen(int isOpen) {
            IsOpen = isOpen;
        }

        public int getIsPay4() {
            return IsPay4;
        }

        public void setIsPay4(int isPay4) {
            IsPay4 = isPay4;
        }

        public String getReceiveDate() {
            return ReceiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            ReceiveDate = receiveDate;
        }

        public String getTaskBonus() {
            return TaskBonus;
        }

        public void setTaskBonus(String taskBonus) {
            TaskBonus = taskBonus;
        }

        public String getTaskContent() {
            return TaskContent;
        }

        public void setTaskContent(String taskContent) {
            TaskContent = taskContent;
        }

        public int getTaskID() {
            return TaskID;
        }

        public void setTaskID(int taskID) {
            TaskID = taskID;
        }

        public String getTaskIconUrl() {
            return TaskIconUrl;
        }

        public void setTaskIconUrl(String taskIconUrl) {
            TaskIconUrl = taskIconUrl;
        }

        public String getTaskTitle() {
            return TaskTitle;
        }

        public void setTaskTitle(String taskTitle) {
            TaskTitle = taskTitle;
        }

        public String getTaskPrompt() {
            return TaskPrompt;
        }

        public void setTaskPrompt(String taskPrompt) {
            TaskPrompt = taskPrompt;
        }

        public String getTaskUrl() {
            return TaskUrl;
        }

        public void setTaskUrl(String taskUrl) {
            TaskUrl = taskUrl;
        }
    }

   /* public static class UserTaskList {
        private int UserID;
        private int IsFinish = 0;   //1打开奖励
        private String ReceiveDate;
        private String TaskIconUrl;
        private String TaskTitle;
        private String TaskContent;
        private String TaskUrl;
        private String TaskBonus;
        private String TaskPrompt;
        private String AppPackage;
        private int     IsOpen     = 0;//1完成任务
        private boolean IsStart    = false;   //是否正在进行任务
        private int     isProgress = 0;//任务的进度条
        private int     Flag       = 0;  //1任务正在下载,,2线程上限,任务正在等待,3任务已被抢光

        private DownloadRecord mRecord;
        private String         saveName;

        private int TaskID;




        //正在进行中的任务和已完成的任务  需要的flag
        private String TimeFlag;
        //是否显示线条
        private boolean isShowLine = true;

        private int IsInstall = 0;  //1应用是通过下载的APK文件安装的

        public int getIsInstall() {
            return IsInstall;
        }

        public void setIsInstall(int isInstall) {
            IsInstall = isInstall;
        }

        public boolean isShowLine() {
            return isShowLine;
        }

        public void setShowLine(boolean showLine) {
            isShowLine = showLine;
        }

        public String getTimeFlag() {
            return TimeFlag;
        }

        public void setTimeFlag(String timeFlag) {
            TimeFlag = timeFlag;
        }

        public int getTaskID() {
            return TaskID;
        }

        public void setTaskID(int taskID) {
            TaskID = taskID;
        }

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        public DownloadRecord getRecord() {
            return mRecord;
        }

        public void setRecord(DownloadRecord record) {
            mRecord = record;
        }

        public int getFlag() {
            return Flag;
        }

        public void setFlag(int flag) {
            Flag = flag;
        }

        public boolean isStart() {
            return IsStart;
        }

        public void setStart(boolean start) {
            IsStart = start;
        }

        public int getIsProgress() {
            return isProgress;
        }

        public void setIsProgress(int isProgress) {
            this.isProgress = isProgress;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getIsFinish() {
            return IsFinish;
        }

        public void setIsFinish(int IsFinish) {
            this.IsFinish = IsFinish;
        }

        public String getReceiveDate() {
            return ReceiveDate;
        }

        public void setReceiveDate(String ReceiveDate) {
            this.ReceiveDate = ReceiveDate;
        }

        public String getTaskIconUrl() {
            return TaskIconUrl;
        }

        public void setTaskIconUrl(String TaskIconUrl) {
            this.TaskIconUrl = TaskIconUrl;
        }

        public String getTaskTitle() {
            return TaskTitle;
        }

        public void setTaskTitle(String TaskTitle) {
            this.TaskTitle = TaskTitle;
        }

        public String getTaskContent() {
            return TaskContent;
        }

        public void setTaskContent(String TaskContent) {
            this.TaskContent = TaskContent;
        }

        public String getTaskUrl() {
            return TaskUrl;
        }

        public void setTaskUrl(String TaskUrl) {
            this.TaskUrl = TaskUrl;
        }

        public String getTaskBonus() {
            return TaskBonus;
        }

        public void setTaskBonus(String TaskBonus) {
            this.TaskBonus = TaskBonus;
        }

        public String getTaskPrompt() {
            return TaskPrompt;
        }

        public void setTaskPrompt(String TaskPrompt) {
            this.TaskPrompt = TaskPrompt;
        }

        public String getAppPackage() {
            return AppPackage;
        }

        public void setAppPackage(String AppPackage) {
            this.AppPackage = AppPackage;
        }

        public int getIsOpen() {
            return IsOpen;
        }

        public void setIsOpen(int IsOpen) {
            this.IsOpen = IsOpen;
        }
    }*/
}
