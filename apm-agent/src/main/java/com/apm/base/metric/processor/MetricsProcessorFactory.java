package com.apm.base.metric.processor;

import com.apm.base.metric.processor.kafka.*;

/**
 * @author Yush
 */
public class MetricsProcessorFactory {


    public static JvmClassMetricsProcessor getClassMetricsProcessor() {
        return new KafkaJvmClassMetricsProcessor();
    }

    public static JvmGCMetricsProcessor getGCMetricsProcessor() {

        return new KafkaJvmGCMetricsProcessor();
    }

    public static JvmMemoryMetricsProcessor getMemoryMetricsProcessor() {
        return new KafkaJvmMemoryMetricsProcessor();
    }

    public static JvmThreadMetricsProcessor getThreadMetricsProcessor() {
        return new KafkaJvmThreadMetricsProcessor();
    }

    public static MethodMetricsProcessor getMethodMetricsProcessor() {
        return new KafkaMethodMetricsProcess();
    }

}
