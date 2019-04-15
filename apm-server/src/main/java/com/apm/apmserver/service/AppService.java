package com.apm.apmserver.service;

import com.apm.apmserver.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 13:50
 * @Description:
 *
 */
@Service
public class AppService {

    @Autowired
    private RedisDao redisDao;


    public List<String> apps() {
        return redisDao.getAppList();
    }


    public List<String> methods(String app, boolean hasClass) {

        return redisDao.getMethodList(app, hasClass);
    }
}
