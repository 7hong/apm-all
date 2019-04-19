package com.apm.base.metric.processor.kafka;

import com.apm.base.MethodTag;
import com.apm.base.config.ApmConfig;
import com.apm.base.metric.MethodMetrics;
import com.apm.base.metric.processor.AbstractMethodMetricsProcessor;
import com.apm.base.util.Logger;
import com.apm.base.util.NumFormatUtils;
import com.apm.kafka.KafkaProducer;
import com.apm.kafka.KafkaTopic;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/16 15:08
 * @Description:
 */
public class KafkaMethodMetricsProcess extends AbstractMethodMetricsProcessor {

    private static final int MAX_LENGTH = 512;

    private KafkaProducer kafkaProducer = new KafkaProducer();

    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };

    @Override
    public void process(MethodMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
            String msg = createLineProtocol(metrics, startMillis, sb);
            Logger.info("msg"+msg);
            kafkaProducer.send(KafkaTopic.TOPIC_METHOD, System.currentTimeMillis()+"", msg);
        } finally {
            sb.setLength(0);
        }
    }

    private String createLineProtocol(MethodMetrics methodMetrics, long startNanos, StringBuilder sb) {
        int suitSize = getSuitSize(methodMetrics);
        if (suitSize > MAX_LENGTH) {
            sb = new StringBuilder(suitSize);
        }
        sb.append(startNanos).append(";")
                .append(ApmConfig.getInstance().getAppId()).append(";")//appid
                .append(ApmConfig.getInstance().getAppName()).append(";")//appName
//                .append(methodMetrics.getMethodTag().getClassName()).append(";")//服务
                .append(methodMetrics.getMethodTag().getFullMethodName()).append(";")
                .append(methodMetrics.getRPS()).append(";")
                .append(NumFormatUtils.getFormatStr(methodMetrics.getAvgTime())).append(";")//avg
                .append(methodMetrics.getMinTime()).append(";")//min
                .append(methodMetrics.getMaxTime()).append(";")
                .append(methodMetrics.getTP90()).append(";")//tp90
                .append(methodMetrics.getTP99()).append(";")//tp99
                .append(methodMetrics.getTP100());//tp100
        return sb.toString();
    }

    private int getSuitSize(MethodMetrics methodMetrics) {
        MethodTag methodTag = methodMetrics.getMethodTag();
        return methodTag.getClassName().length()
                + 8 + methodTag.getFullMethodName().length()//Method
                + 5 + 6 + 1//RPS
                + 5 + 7 //Avg
                + 5 + 3 + 1//Min
                + 5 + 5 + 1//Max
//                + 8 + 7//StdDev
//                + 7 + 8 + 1//Count
//                + 6 + 5 + 1//TP50
                + 6 + 5 + 1//TP90
//                + 6 + 5 + 1//TP95
//                + 6 + 5 + 1//TP99
//                + 7 + 5 + 1//TP999
//                + 8 + 5 + 1//TP9999
//                + 9 + 5 + 1//TP99999
                + 7 + 5 + 1//TP100
                + 1 + 21//startNanos
                ;
    }
}
