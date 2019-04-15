package com.apm.base.metric.processor.log;

import com.apm.base.metric.JvmThreadMetrics;
import com.apm.base.metric.formatter.JvmThreadMetricsFormatter;
import com.apm.base.metric.formatter.impl.DefaultJvmThreadMetricsFormatter;
import com.apm.base.metric.processor.AbstractJvmThreadMetricsProcessor;
import com.apm.base.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jiang Qihong on 2018/8/25
 */
public class LoggerJvmThreadMetricsProcessor extends AbstractJvmThreadMetricsProcessor {

    private ConcurrentHashMap<Long, List<JvmThreadMetrics>> metricsMap = new ConcurrentHashMap<>(8);

    private JvmThreadMetricsFormatter metricsFormatter = new DefaultJvmThreadMetricsFormatter();

    @Override
    public void beforeProcess(long processId, long startMillis, long stopMillis) {
        metricsMap.put(processId, new ArrayList<JvmThreadMetrics>(1));
    }

    @Override
    public void process(JvmThreadMetrics metrics, long processId, long startMillis, long stopMillis) {
        List<JvmThreadMetrics> metricsList = metricsMap.get(processId);
        if (metricsList != null) {
            metricsList.add(metrics);
        } else {
            Logger.error("LoggerJvmThreadMetricsProcessor.process(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }

    @Override
    public void afterProcess(long processId, long startMillis, long stopMillis) {
        List<JvmThreadMetrics> metricsList = metricsMap.remove(processId);
        if (metricsList != null) {
            logger.logAndFlush(metricsFormatter.format(metricsList, startMillis, stopMillis));
        } else {
            Logger.error("LoggerJvmThreadMetricsProcessor.afterProcess(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }
}
