package com.apm.base.metric.processor.log;

import com.apm.base.metric.JvmMemoryMetrics;
import com.apm.base.metric.formatter.JvmMemoryMetricsFormatter;
import com.apm.base.metric.formatter.impl.DefaultJvmMemoryMetricsFormatter;
import com.apm.base.metric.processor.AbstractJvmMemoryMetricsProcessor;
import com.apm.base.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jiang Qihong on 2018/8/25
 */
public class LoggerJvmMemoryMetricsProcessor extends AbstractJvmMemoryMetricsProcessor {

    private ConcurrentHashMap<Long, List<JvmMemoryMetrics>> metricsMap = new ConcurrentHashMap<>(8);

    private JvmMemoryMetricsFormatter metricsFormatter = new DefaultJvmMemoryMetricsFormatter();

    @Override
    public void beforeProcess(long processId, long startMillis, long stopMillis) {
        metricsMap.put(processId, new ArrayList<JvmMemoryMetrics>(2));
    }

    @Override
    public void process(JvmMemoryMetrics metrics, long processId, long startMillis, long stopMillis) {
        List<JvmMemoryMetrics> metricsList = metricsMap.get(processId);
        if (metricsList != null) {
            metricsList.add(metrics);
        } else {
            Logger.error("LoggerJvmMemoryMetricsProcessor.process(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }

    @Override
    public void afterProcess(long processId, long startMillis, long stopMillis) {
        List<JvmMemoryMetrics> metricsList = metricsMap.remove(processId);
        if (metricsList != null) {
            logger.logAndFlush(metricsFormatter.format(metricsList, startMillis, stopMillis));
        } else {
            Logger.error("LoggerJvmMemoryMetricsProcessor.afterProcess(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }
}
