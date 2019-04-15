package com.apm.apmserver.bean.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 13:30
 * @Description:
 */
@Table("class_metric")
public class ClassMetric {

    @Column(value = "app_id")
    private String appid;

    private Long time;

    @PrimaryKey
    @Column(value = "app_name")
    private String appName;

    private long total;

    private long loaded;

    private long unloaded;

    public ClassMetric() {
    }

    public ClassMetric(String appid, Long time, String appName, long total, long loaded, long unloaded) {
        this.appid = appid;
        this.time = time;
        this.appName = appName;
        this.total = total;
        this.loaded = loaded;
        this.unloaded = unloaded;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getLoaded() {
        return loaded;
    }

    public void setLoaded(long loaded) {
        this.loaded = loaded;
    }

    public long getUnloaded() {
        return unloaded;
    }

    public void setUnloaded(long unloaded) {
        this.unloaded = unloaded;
    }
}
