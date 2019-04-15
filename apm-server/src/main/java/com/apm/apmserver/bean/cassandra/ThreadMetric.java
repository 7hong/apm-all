package com.apm.apmserver.bean.cassandra;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 13:30
 * @Description:
 */
@Table("thread_metric")
public class ThreadMetric {


    @Column(value = "app_id")
    private String appid;

    private Long time;

    @PrimaryKey
    @Column(value = "app_name")
    private String appName;


    private long totalStarted;

    private int active;

    private int peak;

    private int daemon;

    private int news;

    private int runnable;

    private int blocked;

    private int waiting;

    private int timedWaiting;

    private int terminated;


    public ThreadMetric() {
    }

    public ThreadMetric(String appid, Long time, String appName, long totalStarted, int active, int peak, int daemon, int news, int runnable, int blocked, int waiting, int timedWaiting, int terminated) {
        this.appid = appid;
        this.time = time;
        this.appName = appName;
        this.totalStarted = totalStarted;
        this.active = active;
        this.peak = peak;
        this.daemon = daemon;
        this.news = news;
        this.runnable = runnable;
        this.blocked = blocked;
        this.waiting = waiting;
        this.timedWaiting = timedWaiting;
        this.terminated = terminated;
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

    public long getTotalStarted() {
        return totalStarted;
    }

    public void setTotalStarted(long totalStarted) {
        this.totalStarted = totalStarted;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getPeak() {
        return peak;
    }

    public void setPeak(int peak) {
        this.peak = peak;
    }

    public int getDaemon() {
        return daemon;
    }

    public void setDaemon(int daemon) {
        this.daemon = daemon;
    }

    public int getNews() {
        return news;
    }

    public void setNews(int news) {
        this.news = news;
    }

    public int getRunnable() {
        return runnable;
    }

    public void setRunnable(int runnable) {
        this.runnable = runnable;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public int getTimedWaiting() {
        return timedWaiting;
    }

    public void setTimedWaiting(int timedWaiting) {
        this.timedWaiting = timedWaiting;
    }

    public int getTerminated() {
        return terminated;
    }

    public void setTerminated(int terminated) {
        this.terminated = terminated;
    }
}
