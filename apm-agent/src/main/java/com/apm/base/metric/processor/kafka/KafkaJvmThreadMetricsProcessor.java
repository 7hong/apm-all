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
            //logger.log(createLineProtocol(metrics, startMillis * 1000 * 1000L, sb));
            kafkaProducer.send(KafkaTopic.TOPIC_THREAD,System.currentTimeMillis()+"",getMessage(metrics,startMillis,sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String getMessage(JvmThreadMetrics metrics, long startMillis, StringBuilder sb) {
//        sb.append(startMillis)
//                .append(ApmConfig.getInstance().getAppId()).append(";")//appid
//                .append(ApmConfig.getInstance().getAppName()).append(";")//appName
//                .append(" TotalStarted=").append(metrics.getTotalStarted()).append("i")
//                .append(",Active=").append(metrics.getActive()).append("i")
//                .append(",Peak=").append(metrics.getPeak()).append("i")
//                .append(",Daemon=").append(metrics.getDaemon()).append("i")
//                .append(",New=").append(metrics.getNews()).append("i")
//                .append(",Runnable=").append(metrics.getRunnable()).append("i")
//                .append(",Blocked=").append(metrics.getBlocked()).append("i")
//                .append(",Waiting=").append(metrics.getWaiting()).append("i")
//                .append(",TimedWaiting=").append(metrics.getTimedWaiting()).append("i")
//                .append(",Terminated=").append(metrics.getTerminated()).append("i");
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
