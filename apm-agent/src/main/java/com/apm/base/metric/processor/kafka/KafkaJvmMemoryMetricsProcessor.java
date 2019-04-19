package com.apm.base.metric.processor.kafka;

import com.apm.base.config.ApmConfig;
import com.apm.base.metric.JvmMemoryMetrics;
import com.apm.base.metric.processor.AbstractJvmMemoryMetricsProcessor;
import com.apm.kafka.KafkaProducer;
import com.apm.kafka.KafkaTopic;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 14:24
 * @Description:
 */
public class KafkaJvmMemoryMetricsProcessor extends AbstractJvmMemoryMetricsProcessor {

    private KafkaProducer kafkaProducer = new KafkaProducer();

    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };

    @Override
    public void process(JvmMemoryMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
            kafkaProducer.send(KafkaTopic.TOPIC_MEM, System.currentTimeMillis()+"",getMessage(metrics, startMillis, sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String getMessage(JvmMemoryMetrics metrics, long startMillis, StringBuilder sb) {
        sb.append(startMillis).append(";")
                .append(ApmConfig.getInstance().getAppId()).append(";")
                .append(ApmConfig.getInstance().getAppName()).append(";")
                .append(metrics.getNonHeapInit()).append(";")
                .append(metrics.getNonHeapUsed()).append(";")
                .append(metrics.getNonHeapCommitted()).append(";")
                .append(metrics.getNonHeapMax()).append(";")
                .append(metrics.getHeapInit()).append(";")
                .append(metrics.getHeapUsed()).append(";")
                .append(metrics.getHeapCommitted()).append(";")
                .append(metrics.getHeapMax());

        return sb.toString();
    }
}
