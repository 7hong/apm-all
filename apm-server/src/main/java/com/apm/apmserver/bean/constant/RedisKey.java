package com.apm.apmserver.bean.constant;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 11:37
 * @Description:
 */
public interface RedisKey {


    String KAFKA_TPS = "apm_kafka_tps";


    String REDIS_METHOD = "apm_method_";

    /**
     * app列表
     */
    String REDIS_APP_LIST = "apm_app_list";


    /**
     * 方法列表
     */
    String REDIS_METHOD_LIST = "apm_method_";
}
