package com.apm.base.metric.processor.kafka;

import com.apm.base.config.ApmConfig;
import com.apm.base.metric.JvmClassMetrics;
import com.apm.base.metric.processor.AbstractJvmClassMetricsProcessor;
import com.apm.kafka.KafkaProducer;
import com.apm.kafka.KafkaTopic;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 11:38
 * @Description:
 */
public class KafkaJvmClassMetricsProcessor extends AbstractJvmClassMetricsProcessor {


    private KafkaProducer kafkaProducer = new KafkaProducer();

    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(128);
        }
    };

    @Override
    public void process(JvmClassMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
//            logger.log(createLineProtocol(metrics, startMillis * 1000 * 1000L, sb));
            kafkaProducer.send(KafkaTopic.TOPIC_CLASS, System.currentTimeMillis()+"",getMessage(metrics,startMillis,sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String getMessage(JvmClassMetrics metrics, long startMillis, StringBuilder sb) {
        sb.append(startMillis).append(";")
                .append(ApmConfig.getInstance().getAppId()).append(";")
                .append(ApmConfig.getInstance().getAppName()).append(";")
                .append(metrics.getTotal()).append(";")
                .append(metrics.getLoaded()).append(";")
                .append(metrics.getUnloaded());
        return sb.toString();
    }

}
