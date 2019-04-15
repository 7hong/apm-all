package com.apm.apmserver.kafka;

import com.apm.apmserver.dao.RedisDao;
import com.apm.apmserver.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者 spring-kafka 2.0 + 依赖JDK8
 */
@Component
public class KafkaConsumer {


    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private RedisDao redisDao;

    /**
     * 消费方法数据
     * @param message
     */
    @KafkaListener(topics = {KafkaTopic.TOPIC_METHOD})
    public void msgMethod(String message){

        redisDao.incrementKafkaTps();
        logger.info("consumer methodMetric {}", message);
        consumerService.saveMethodMetrics(message);


    }

    /**
     * 消费方法数据
     * @param message
     */
    @KafkaListener(topics = {KafkaTopic.TOPIC_GC})
    public void msgGc(String message){
        redisDao.incrementKafkaTps();
        logger.info("consumer gcMetric {}", message);
        consumerService.saveGcMetrics(message);

    }

    /**
     * 消费方法数据
     * @param message
     */
    @KafkaListener(topics = {KafkaTopic.TOPIC_MEM})
    public void msgMem(String message){
        redisDao.incrementKafkaTps();
        logger.info("consumer memaryMetric {}", message);
        consumerService.saveMemMetrics(message);
    }

    /**
     * 消费方法数据
     * @param message
     */
    @KafkaListener(topics = {KafkaTopic.TOPIC_THREAD})
    public void msgThread(String message){
        redisDao.incrementKafkaTps();
        logger.info("consumer threadMetric {}", message);
        consumerService.saveThreadMetrics(message);
    }

    /**
     * 消费方法数据
     * @param message
     */
    @KafkaListener(topics = {KafkaTopic.TOPIC_CLASS})
    public void msgClass(String message){
        redisDao.incrementKafkaTps();
        logger.info("consumer classMetric {}", message);
        consumerService.saveClassMetrics(message);
    }
}