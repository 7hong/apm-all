package com.apm.apmserver.bean;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 16:11
 * @Description:
 */
public class SearchBean {

    private String app;

    private String service;

    private String method;

    private long startTime;

    private long endTime;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
