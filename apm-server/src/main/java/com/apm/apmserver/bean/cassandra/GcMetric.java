package com.apm.apmserver.bean.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 13:30
 * @Description:
 */
@Table("gc_metric")
public class GcMetric {

    @Column(value = "app_id")
    private String appid;

    private Long time;

    @PrimaryKey
    @Column(value = "app_name")
    private String appName;

    private String gcName;

    private long collectCount;

    private long collectTime;


    public GcMetric() {
    }


    public GcMetric(String appid, Long time, String appName, String gcName, long collectCount, long collectTime) {
        this.appid = appid;
        this.time = time;
        this.appName = appName;
        this.gcName = gcName;
        this.collectCount = collectCount;
        this.collectTime = collectTime;
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(long collectCount) {
        this.collectCount = collectCount;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }
}
