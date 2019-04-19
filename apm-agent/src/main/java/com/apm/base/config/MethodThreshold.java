package com.apm.base.config;

/**
 * 单位:ms
 */
public class MethodThreshold {

    private int time;

    private int count;

    public MethodThreshold(int time, int count) {
        this.time = time;
        this.count = count;
    }


    public static MethodThreshold of(int time, int count) {
        return new MethodThreshold(time, count);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MethodThreshold{" +
                "time=" + time +
                ", count=" + count +
                '}';
    }
}
