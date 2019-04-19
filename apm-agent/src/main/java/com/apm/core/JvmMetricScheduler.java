package com.apm.core;

import com.apm.base.Scheduler;
import com.apm.base.util.ExecutorManager;
import com.apm.base.util.Logger;
import com.apm.base.util.ThreadUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JvmMetricScheduler {

    private static final ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(2,
            ThreadUtils.newThreadFactory("apm-JvmMetricScheduler-"),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    private final Scheduler scheduler;

    private final long initialDelay;

    private final long period;

    private final TimeUnit unit;

    private final long millTimeSlice;

    private volatile long nextTimeSliceEndTime = 0L;

    public JvmMetricScheduler(Scheduler scheduler,
                              long initialDelay,
                              long period,
                              TimeUnit unit,
                              long millTimeSlice) {
        this.scheduler = scheduler;
        this.millTimeSlice = millTimeSlice;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    public static void initScheduleTask(Scheduler scheduler, long millTimeSlice) {
        ExecutorManager.addExecutorService(scheduledExecutor);

        new JvmMetricScheduler(scheduler, 0, 10, TimeUnit.MILLISECONDS, millTimeSlice).start();
    }


    private void start() {
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentMills = System.currentTimeMillis();
                if (nextTimeSliceEndTime == 0L) {
                    nextTimeSliceEndTime = ((currentMills / millTimeSlice) * millTimeSlice) + millTimeSlice;
                }

                //还在当前的时间片里
                if (nextTimeSliceEndTime > currentMills) {
                    return;
                }
                nextTimeSliceEndTime = ((currentMills / millTimeSlice) * millTimeSlice) + millTimeSlice;
                runTasks(currentMills);
            }
        }, initialDelay, period, unit);
    }

    private void runTasks(long currentMills) {

        try {
            long lastTimeSliceStartTime = currentMills - millTimeSlice;
            runTask(scheduler, lastTimeSliceStartTime);
        } finally {
            Logger.debug("JvmMetricScheduler.runTasks() cost: " + (System.currentTimeMillis() - currentMills) + "ms");
        }
    }

    private void runTask(Scheduler scheduler, long lastTimeSliceStartTime) {
        try {
            scheduler.run(lastTimeSliceStartTime, millTimeSlice);
        } catch (Exception e) {
            Logger.error("JvmMetricScheduler.runTask(" + scheduler + ", " + lastTimeSliceStartTime + ")", e);
        }
    }

}
