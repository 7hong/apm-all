package com.apm.base.metric.processor.kafka;

import com.apm.base.config.ApmConfig;
import com.apm.base.metric.JvmThreadMetrics;
import com.apm.base.metric.processor.AbstractJvmThreadMetricsProcessor;
import com.apm.kafka.KafkaProducer;
import com.apm.kafka.KafkaTopic;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/23 11:23
 * @Description:
 */
public class KafkaJvmThreadMetricsProcessor extends AbstractJvmThreadMetricsProcessor {


    private KafkaProducer kafkaProducer = new KafkaProducer();


    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {

        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };

    @Override
    public void process(JvmThreadMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
            kafkaProducer.send(KafkaTopic.TOPIC_THREAD,System.currentTimeMillis()+"",getMessage(metrics,startMillis,sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String getMessage(JvmThreadMetrics metrics, long startMillis, StringBuilder sb) {
        sb.append(startMillis).append(";")
                .append(ApmConfig.getInstance().getAppId()).append(";")//appid
                .append(ApmConfig.getInstance().getAppName()).append(";")//appName
                .append(metrics.getTotalStarted()).append(";")
                .append(metrics.getActive()).append(";")
                .append(metrics.getPeak()).append(";")
                .append(metrics.getDaemon()).append(";")
                .append(metrics.getNews()).append(";")
                .append(metrics.getRunnable()).append(";")
                .append(metrics.getBlocked()).append(";")
                .append(metrics.getWaiting()).append(";")
                .append(metrics.getTimedWaiting()).append(";")
                .append(metrics.getTerminated());
        return sb.toString();
    }
}
