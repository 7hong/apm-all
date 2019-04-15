package com.apm.apmserver.bean.cassandra;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 11:01
 * @Description:
 */
@Table("method_metric")
public class MethodMetric implements Serializable{

//    @PrimaryKey
//    @CassandraType(type = DataType.Name.UUID)
//    private UUID id;

    @Column(value = "app_id")
    private String appid;

    private Long time;

    @PrimaryKey
    @Column(value = "app_name")
    private String appName;

    @Column(value = "class_name")
    private String className;

    @Column(value = "method_name")
    private String methodName;

    private int rps;

    private double avg;

    private int min;

    private int max;

    private int tp90;

    private int tp99;

    private int tp100;


    public MethodMetric() {
    }

    public MethodMetric(Long time, String appid, String appName, String className, String methodName, int rps, double avg, int min, int max, int tp90, int tp99, int tp100) {
        this.time = time;
        this.appid = appid;
        this.appName = appName;
        this.className = className;
        this.methodName = methodName;
        this.rps = rps;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.tp90 = tp90;
        this.tp99 = tp99;
        this.tp100 = tp100;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getRps() {
        return rps;
    }

    public void setRps(int rps) {
        this.rps = rps;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTp90() {
        return tp90;
    }

    public void setTp90(int tp90) {
        this.tp90 = tp90;
    }

    public int getTp99() {
        return tp99;
    }

    public void setTp99(int tp99) {
        this.tp99 = tp99;
    }

    public int getTp100() {
        return tp100;
    }

    public void setTp100(int tp100) {
        this.tp100 = tp100;
    }

    @Override
    public String toString() {
        return "MethodMetric{" +
                "appid='" + appid + '\'' +
                ", time=" + time +
                ", appName='" + appName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", rps=" + rps +
                ", avg=" + avg +
                ", min=" + min +
                ", max=" + max +
                ", tp90=" + tp90 +
                ", tp99=" + tp99 +
                ", tp100=" + tp100 +
                '}';
    }
}
