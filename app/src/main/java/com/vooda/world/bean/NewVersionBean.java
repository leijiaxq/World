package com.vooda.world.bean;

/**
 * Create by  leijiaxq
 * Date       2017/2/23 13:40
 * Describe   新版本信息
 */
public class NewVersionBean {


    /**
     * Message : 获取最新版本
     * Version : 2.0
     * AppUrl : http://www.powerll.com/api/appUpdate/zztx.apk
     * Detail : 掌赚天下2.0更新如下:/n1、修复已知bug/n2、增加返佣力度
     * Result : ok
     */

    private String Message;
    private String Version;
    private String AppUrl;
    private String Detail;
    private String Result;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getAppUrl() {
        return AppUrl;
    }

    public void setAppUrl(String AppUrl) {
        this.AppUrl = AppUrl;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }
}
