package com.apm.base.metric.processor.log;

import com.apm.base.metric.MethodMetrics;
import com.apm.base.metric.formatter.MethodMetricsFormatter;
import com.apm.base.metric.formatter.impl.DefaultMethodMetricsFormatter;
import com.apm.base.metric.processor.AbstractMethodMetricsProcessor;
import com.apm.base.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jiang Qihong on 2018/7/11
 */
public class LoggerMethodMetricsProcessor extends AbstractMethodMetricsProcessor {

    private ConcurrentHashMap<Long, List<MethodMetrics>> metricsMap = new ConcurrentHashMap<>(8);

    private MethodMetricsFormatter formatter = new DefaultMethodMetricsFormatter();

    @Override
    public void beforeProcess(long processId, long startMillis, long stopMillis) {
        metricsMap.put(processId, new ArrayList<MethodMetrics>(64));
    }

    @Override
    public void process(MethodMetrics metrics, long processId, long startMillis, long stopMillis) {
        List<MethodMetrics> metricsList = metricsMap.get(processId);
        if (metricsList != null) {
            metricsList.add(metrics);
        } else {
            Logger.error("LoggerMethodMetricsProcessor.process(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }

    @Override
    public void afterProcess(long processId, long startMillis, long stopMillis) {
        List<MethodMetrics> metricsList = metricsMap.remove(processId);
        if (metricsList != null) {
            logger.logAndFlush(formatter.format(metricsList, startMillis, stopMillis));
        } else {
            Logger.error("LoggerMethodMetricsProcessor.afterProcess(" + processId + ", " + startMillis + ", " + stopMillis + "): metricsList is null!!!");
        }
    }
}
