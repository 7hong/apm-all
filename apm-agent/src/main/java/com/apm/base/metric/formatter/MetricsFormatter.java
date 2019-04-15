package com.apm.base.metric.formatter;

import com.apm.base.metric.Metrics;

import java.util.List;

/**
 * Created by Jiang Qihong on 2018/8/21
 */
public interface MetricsFormatter<T extends Metrics> {

    String format(List<T> metricsList, long startMillis, long stopMillis);

}
