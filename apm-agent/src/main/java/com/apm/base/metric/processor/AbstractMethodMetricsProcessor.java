package com.apm.base.metric.processor;

import com.apm.base.config.ApmConfig;
import com.apm.base.log.ILogger;
import com.apm.base.log.LoggerFactory;

/**
 * Created by Jiang Qihong on 2018/8/25
 */
public abstract class AbstractMethodMetricsProcessor implements MethodMetricsProcessor {

    protected ILogger logger = LoggerFactory.getLogger(ApmConfig.getInstance().getMethodMetricsFile());


    @Override
    public void beforeProcess(long processId, long startMillis, long stopMillis) {
        //empty
    }

    @Override
    public void afterProcess(long processId, long startMillis, long stopMillis) {
        logger.flushLog();
    }
}
