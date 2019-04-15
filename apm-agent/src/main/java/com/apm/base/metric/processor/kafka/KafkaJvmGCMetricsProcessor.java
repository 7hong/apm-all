package com.apm.base.metric.processor.kafka;

import com.apm.base.config.ApmConfig;
import com.apm.base.metric.JvmGCMetrics;
import com.apm.base.metric.processor.AbstractJvmGCMetricsProcessor;
import com.apm.base.util.LineProtocolUtils;
import com.apm.kafka.KafkaProducer;
import com.apm.kafka.KafkaTopic;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 13:23
 * @Description:
 */
public class KafkaJvmGCMetricsProcessor extends AbstractJvmGCMetricsProcessor {

    private KafkaProducer kafkaProducer = new KafkaProducer();

    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(128);
        }
    };

    @Override
    public void process(JvmGCMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
            kafkaProducer.send(KafkaTopic.TOPIC_GC, System.currentTimeMillis()+"",getMessage(metrics, startMillis, sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String getMessage(JvmGCMetrics metrics, long startMillis, StringBuilder sb) {
        String gcName = LineProtocolUtils.processTagOrField(metrics.getGcName());
        sb.append(startMillis).append(";")
                .append(ApmConfig.getInstance().getAppId()).append(";")
                .append(ApmConfig.getInstance().getAppName()).append(";")
                .append(gcName).append(";")
                .append(metrics.getCollectCount()).append(";")
                .append(metrics.getCollectTime());
        return sb.toString();
    }
}
