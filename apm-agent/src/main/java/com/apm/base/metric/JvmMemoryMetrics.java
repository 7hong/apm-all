package com.apm.base.metric;

import java.lang.management.MemoryUsage;

/**
 * Created by Jiang Qihong on 2018/8/19
 */
public class JvmMemoryMetrics extends Metrics {

    private static final long serialVersionUID = -1501873958253505089L;

    private long nonHeapInit;

    private long nonHeapUsed;

    private long nonHeapCommitted;

    private long nonHeapMax;


    private long heapInit;

    private long heapUsed;

    private long heapCommitted;

    private long heapMax;

    public JvmMemoryMetrics(long nonHeapInit, long nonHeapUsed, long nonHeapCommitted, long nonHeapMax, long heapInit, long heapUsed, long heapCommitted, long heapMax) {
        this.nonHeapInit = nonHeapInit;
        this.nonHeapUsed = nonHeapUsed;
        this.nonHeapCommitted = nonHeapCommitted;
        this.nonHeapMax = nonHeapMax;
        this.heapInit = heapInit;
        this.heapUsed = heapUsed;
        this.heapCommitted = heapCommitted;
        this.heapMax = heapMax;
    }


    public JvmMemoryMetrics(MemoryUsage nonHeapMem, MemoryUsage heapMem) {
        this(nonHeapMem.getInit(), nonHeapMem.getUsed(), nonHeapMem.getCommitted(), nonHeapMem.getMax(),
                heapMem.getInit(), heapMem.getUsed(), heapMem.getCommitted(), heapMem.getMax());
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

    @Override
    public String toString() {
        return "JvmMemoryMetrics{" +
                "nonHeapInit=" + nonHeapInit +
                ", nonHeapUsed=" + nonHeapUsed +
                ", nonHeapCommitted=" + nonHeapCommitted +
                ", nonHeapMax=" + nonHeapMax +
                ", heapInit=" + heapInit +
                ", heapUsed=" + heapUsed +
                ", heapCommitted=" + heapCommitted +
                ", heapMax=" + heapMax +
                '}';
    }
}
