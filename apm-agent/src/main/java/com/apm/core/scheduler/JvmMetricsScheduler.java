package com.apm.core.scheduler;


import com.apm.base.Scheduler;
import com.apm.base.metric.JvmClassMetrics;
import com.apm.base.metric.JvmGCMetrics;
import com.apm.base.metric.JvmMemoryMetrics;
import com.apm.base.metric.JvmThreadMetrics;
import com.apm.base.metric.processor.*;

import java.lang.management.*;
import java.util.List;

/**
 * Created by Jiang Qihong on 2018/8/22
 */
public class JvmMetricsScheduler implements Scheduler {

    private JvmClassMetricsProcessor classMetricsProcessor;

    private JvmGCMetricsProcessor gcMetricsProcessor;

    private JvmMemoryMetricsProcessor memoryMetricsProcessor;

    private JvmThreadMetricsProcessor threadMetricsProcessor;


    public JvmMetricsScheduler(JvmClassMetricsProcessor classMetricsProcessor,
                               JvmGCMetricsProcessor gcMetricsProcessor,
                               JvmMemoryMetricsProcessor memoryMetricsProcessor,
                               JvmThreadMetricsProcessor threadMetricsProcessor) {
        this.classMetricsProcessor = classMetricsProcessor;
        this.gcMetricsProcessor = gcMetricsProcessor;
        this.memoryMetricsProcessor = memoryMetricsProcessor;
        this.threadMetricsProcessor = threadMetricsProcessor;
    }

    @Override
    public void run(long lastTimeSliceStartTime, long millTimeSlice) {
        long stopMillis = lastTimeSliceStartTime + millTimeSlice;
        processJVMClassMetrics(lastTimeSliceStartTime, lastTimeSliceStartTime, stopMillis);
        processJVMGCMetrics(lastTimeSliceStartTime, lastTimeSliceStartTime, stopMillis);
        processJVMMemoryMetrics(lastTimeSliceStartTime, lastTimeSliceStartTime, stopMillis);
        processJVMThreadMetrics(lastTimeSliceStartTime, lastTimeSliceStartTime, stopMillis);
    }

    private void processJVMClassMetrics(long processId, long startMillis, long stopMillis) {
        JvmClassMetrics classMetrics = new JvmClassMetrics(ManagementFactory.getClassLoadingMXBean());

        classMetricsProcessor.beforeProcess(processId, startMillis, stopMillis);
        try {
            classMetricsProcessor.process(classMetrics, processId, startMillis, stopMillis);
        } finally {
            classMetricsProcessor.afterProcess(processId, startMillis, stopMillis);
        }
    }

    private void processJVMGCMetrics(long processId, long startMillis, long stopMillis) {
        gcMetricsProcessor.beforeProcess(processId, startMillis, stopMillis);
        try {
            List<GarbageCollectorMXBean> garbageCollectorMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean bean : garbageCollectorMxBeans) {
                JvmGCMetrics gcMetrics = new JvmGCMetrics(bean);
                gcMetricsProcessor.process(gcMetrics, processId, startMillis, stopMillis);
            }
        } finally {
            gcMetricsProcessor.afterProcess(processId, startMillis, stopMillis);
        }
    }

    private void processJVMMemoryMetrics(long processId, long startMillis, long stopMillis) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage nonHeapMem = memoryMXBean.getNonHeapMemoryUsage();
        MemoryUsage heapMem = memoryMXBean.getHeapMemoryUsage();

        memoryMetricsProcessor.beforeProcess(processId, startMillis, stopMillis);
        try {
            memoryMetricsProcessor.process(new JvmMemoryMetrics(nonHeapMem, heapMem), processId, startMillis, stopMillis);
        } finally {
            memoryMetricsProcessor.afterProcess(processId, startMillis, stopMillis);
        }
    }

    private void processJVMThreadMetrics(long processId, long startMillis, long stopMillis) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        threadMetricsProcessor.beforeProcess(processId, startMillis, stopMillis);
        try {
            threadMetricsProcessor.process(new JvmThreadMetrics(threadMXBean), processId, startMillis, stopMillis);
        } finally {
            threadMetricsProcessor.afterProcess(processId, startMillis, stopMillis);
        }
    }

}
