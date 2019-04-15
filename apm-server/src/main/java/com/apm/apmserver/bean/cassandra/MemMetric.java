package com.apm.apmserver.bean.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 13:30
 * @Description:
 */
@Table("mem_metric")
public class MemMetric {


    @Column(value = "app_id")
    private String appid;

    private Long time;

    @PrimaryKey
    @Column(value = "app_name")
    private String appName;

    private long nonHeapInit;

    private long nonHeapUsed;

    private long nonHeapCommitted;

    private long nonHeapMax;


    private long heapInit;

    private long heapUsed;

    private long heapCommitted;

    private long heapMax;

    public MemMetric() {
    }

    public MemMetric(String appid, Long time, String appName, long nonHeapInit, long nonHeapUsed, long nonHeapCommitted, long nonHeapMax, long heapInit, long heapUsed, long heapCommitted, long heapMax) {
        this.appid = appid;
        this.time = time;
        this.appName = appName;
        this.nonHeapInit = nonHeapInit;
        this.nonHeapUsed = nonHeapUsed;
        this.nonHeapCommitted = nonHeapCommitted;
        this.nonHeapMax = nonHeapMax;
        this.heapInit = heapInit;
        this.heapUsed = heapUsed;
        this.heapCommitted = heapCommitted;
        this.heapMax = heapMax;
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

    public long getNonHeapInit() {
        return nonHeapInit;
    }

    public void setNonHeapInit(long nonHeapInit) {
        this.nonHeapInit = nonHeapInit;
    }

    public long getNonHeapUsed() {
        return nonHeapUsed;
    }

    public void setNonHeapUsed(long nonHeapUsed) {
        this.nonHeapUsed = nonHeapUsed;
    }

    public long getNonHeapCommitted() {
        return nonHeapCommitted;
    }

    public void setNonHeapCommitted(long nonHeapCommitted) {
        this.nonHeapCommitted = nonHeapCommitted;
    }

    public long getNonHeapMax() {
        return nonHeapMax;
    }

    public void setNonHeapMax(long nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    public long getHeapInit() {
        return heapInit;
    }

    public void setHeapInit(long heapInit) {
        this.heapInit = heapInit;
    }

    public long getHeapUsed() {
        return heapUsed;
    }

    public void setHeapUsed(long heapUsed) {
        this.heapUsed = heapUsed;
    }

    public long getHeapCommitted() {
        return heapCommitted;
    }

    public void setHeapCommitted(long heapCommitted) {
        this.heapCommitted = heapCommitted;
    }

    public long getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(long heapMax) {
        this.heapMax = heapMax;
    }
}
