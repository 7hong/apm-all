package com.apm.apmserver.dao;

import com.apm.apmserver.bean.constant.RedisKey;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 11:26
 * @Description:
 */
@Component
public class RedisDao {

    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;


    /**
     * 添加应用和方法
     * @param app
     * @param method
     */
    public void addMethod(String app, String method) {
        if (redisTemplate == null) {
            if (RedisData.methods.get(app) != null) {
                RedisData.methods.get(app).add(method);
            } else {
                Set<String> s = new HashSet<>();
                s.add(method);
                RedisData.methods.put(app, s);
            }
        } else {
            SetOperations<Serializable, Serializable> setOperations = redisTemplate.opsForSet();
            setOperations.add(RedisKey.REDIS_APP_LIST, app);
            setOperations.add(RedisKey.REDIS_METHOD+":"+app, method);
        }

    }


    /**
     * 获取应用列表
     * @return
     */
    public List<String> getAppList() {

        if (redisTemplate == null) {

            return RedisData.apps.stream().collect(Collectors.toList());

        } else {
            SetOperations<Serializable, Serializable> setOperations = redisTemplate.opsForSet();
            Set<Serializable> set = setOperations.members(RedisKey.REDIS_APP_LIST);
            Iterator<Serializable> iterator = set.iterator();
            List<String> list = new ArrayList<>();
            while(iterator.hasNext()) {
                String app = (String)iterator.next();
                list.add(app);
            }
            return list;
        }
    }


    /**
     *
     * @param app
     * @param hasClass 是否包含类名 前缀
     * @return
     */
    public List<String> getMethodList(String app ,boolean hasClass) {

        if (redisTemplate == null) {
            return RedisData.methods.get(app).stream().collect(Collectors.toList());
        } else {
            SetOperations<Serializable, Serializable> setOperations = redisTemplate.opsForSet();
            Set<Serializable> keys = setOperations.members(RedisKey.REDIS_METHOD_LIST + ":"+ app);
            List<String> list = new ArrayList<>();
            Iterator<Serializable> iterator = keys.iterator();
            while(iterator.hasNext()) {
                String m = (String)iterator.next();
                if (!hasClass) {
                    int i = m.lastIndexOf(".");
                    m = m.substring(i+1);
                }
                list.add(m);
            }
            return list;
        }

    }
}
