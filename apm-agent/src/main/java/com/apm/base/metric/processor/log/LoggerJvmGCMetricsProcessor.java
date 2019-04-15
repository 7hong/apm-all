package com.apm.base.metric.processor.log;

import com.apm.base.metric.JvmGCMetrics;
import com.apm.base.metric.formatter.JvmGCMetricsFormatter;
import com.apm.base.metric.formatter.impl.DefaultJvmGCMetricsFormatter;
import com.apm.base.metric.processor.AbstractJvmGCMetricsProcessor;
import com.apm.base.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class LoggerJvmGCMetricsProcessor extends AbstractJvmGCMetricsProcessor {

    private ConcurrentHashMap<Long, List<JvmGCMetrics>> metricsMap = new ConcurrentHashMap<>(8);

    private JvmGCMetricsFormatter metricsFormatter = new DefaultJvmGCMetricsFormatter();

    @Override
    public void beforeProcess(long processId, long startMillis, long stopMillis) {
        metricsMap.put(processId, new ArrayList<JvmGCMetrics>(1));
    }

    @Override
    public void process(JvmGCMetrics metrics, long processId, long startMillis, long stopMillis) {
        List<JvmGCMetrics> metricsList = metricsMap.get(processId);
        if (metricsList != null) {
            metricsList.add(metrics);
        } else {
            Logger.error("LoggerJvmGCMetricsProcessor.process(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }

    @Override
    public void afterProcess(long processId, long startMillis, long stopMillis) {
        List<JvmGCMetrics> metricsList = metricsMap.remove(processId);
        if (metricsList != null) {
            logger.logAndFlush(metricsFormatter.format(metricsList, startMillis, stopMillis));
        } else {
            Logger.error("LoggerJvmGCMetricsProcessor.afterProcess(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }
}
