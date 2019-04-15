package com.apm.kafka;


/**
 * Agent上报消息的topic
 */
public interface KafkaTopic {

    String TOPIC_METHOD = "apm_method";

    /**
     * 线程状态
     */
    String TOPIC_THREAD = "apm_thread";

    /**
     * GC
     */
    String TOPIC_GC = "apm_gc";

    /**
     * JVM 堆内存
     */
    String TOPIC_MEM = "apm_mem";

    /**
     * 类加载
     */
    String TOPIC_CLASS = "apm_class";

}
