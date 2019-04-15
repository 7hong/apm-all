package com.apm.apmserver.dao;

import com.google.common.util.concurrent.AtomicLongMap;
import org.apache.kafka.common.cache.LRUCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/31 15:40
 * @Description:
 */
public class AtomicData {


    private AtomicData data = new AtomicData();


    public static AtomicLongMap<Integer> KAFKA_TPS;

    private AtomicData(){
        Map<Integer, Long> map = new HashMap<>(10);
        for (int i = 0; i < 10; i++) {
            map.put(i, 0L);
        }
        KAFKA_TPS = AtomicLongMap.create(map);
    }

    public AtomicData getInatance() {
        return data;
    }
}
