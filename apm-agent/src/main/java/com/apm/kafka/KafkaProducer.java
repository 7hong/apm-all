package com.apm.kafka;

import com.apm.base.config.ApmConfig;
import com.apm.base.util.Logger;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;


/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/16 17:11
 * @Description: Kafka的生产者
 */
public class KafkaProducer {


    private Producer<String, String> producer;

    public void send(String topic, String key, String message) {
        if (producer == null) {
            synchronized (KafkaProducer.class) {
                if (producer == null) {
                    Properties kafkaProp = new Properties();
                    kafkaProp.put("metadata.broker.list", ApmConfig.getInstance().getKafka().trim());
                    kafkaProp.put("key.serializer.class", StringEncoder.class.getCanonicalName());
                    kafkaProp.put("serializer.class", StringEncoder.class.getCanonicalName());
                    producer = new Producer<>(new ProducerConfig(kafkaProp));
                }
            }
        }
//        Logger.debug("kafka producer :" + message);
        producer.send(new KeyedMessage(topic, key, message));
    }
}
