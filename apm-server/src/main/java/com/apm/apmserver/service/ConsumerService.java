package com.apm.apmserver.service;

import com.apm.apmserver.bean.cassandra.*;
import com.apm.apmserver.dao.RedisDao;
import com.apm.apmserver.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 11:00
 * @Description:
 */
@Service
public class ConsumerService {

    Logger logger = LoggerFactory.getLogger(MetricService.class);

    @Resource
    private MethodMetricRepository methodMetricRepository;

    @Resource
    private GcMetricRepository gcMetricRepository;

    @Resource
    private ThreadMetricRepository threadMetricRepository;

    @Resource
    private ClassMetricRepository classMetricRepository;

    @Resource
    private MemMetricRepository memMetricRepository;

    @Resource
    private RedisDao redisDao;


    public void saveMethodMetrics(String message) {

        if(StringUtils.isEmpty(message)) {
            return ;
        }
        String[] msg = message.split(";");
        if (msg.length != 11) {
            return;
        }
        Long time = Long.parseLong(msg[0]);
        String appId = msg[1];
        String app = msg[2];
        String methodName = msg[3];
        int rps = Integer.parseInt(msg[4]);
        double avg = Double.parseDouble(msg[5]);
        int min = Integer.parseInt(msg[6]);
        int max = Integer.parseInt(msg[7]);
        int tp90 = Integer.parseInt(msg[8]);
        int tp99 = Integer.parseInt(msg[9]);
        int tp100 = Integer.parseInt(msg[10]);
        MethodMetric methodMetric = new MethodMetric(time, appId, app, "", methodName, rps, avg, min, max, tp90, tp99, tp100);
        methodMetricRepository.save(methodMetric);
        redisDao.addMethod(app, methodName);
    }

    public void saveClassMetrics(String message) {
        if(StringUtils.isEmpty(message)) {
            return ;
        }
        String[] msg = message.split(";");
        if (msg.length != 6) {
            return;
        }

        String[] m = message.split(";");
        ClassMetric t = new ClassMetric();
        t.setTime(Long.parseLong(m[0]));
        t.setAppid(m[1]);
        t.setAppName(m[2]);
        t.setTotal(Long.parseLong(m[3]));
        t.setLoaded(Long.parseLong(m[4]));
        t.setUnloaded(Long.parseLong(m[5]));
        classMetricRepository.save(t);

    }

    public void saveThreadMetrics(String message) {
        if(StringUtils.isEmpty(message)) {
            return ;
        }
        String[] msg = message.split(";");
        if (msg.length != 13) {
            return;
        }
        String[] m = message.split(";");
        ThreadMetric t = new ThreadMetric();
        t.setTime(Long.parseLong(m[0]));
        t.setAppid(m[1]);
        t.setAppName(m[2]);
        t.setTotalStarted(Long.parseLong(m[3]));
        t.setActive(Integer.parseInt(m[4]));
        t.setPeak(Integer.parseInt(m[5]));
        t.setDaemon(Integer.parseInt(m[6]));
        t.setNews(Integer.parseInt(m[7]));
        t.setRunnable(Integer.parseInt(m[8]));
        t.setBlocked(Integer.parseInt(m[9]));
        t.setWaiting(Integer.parseInt(m[10]));
        t.setTimedWaiting(Integer.parseInt(m[11]));
        t.setTerminated(Integer.parseInt(m[12]));

        threadMetricRepository.save(t);

    }

    public void saveMemMetrics(String message) {
        if(StringUtils.isEmpty(message)) {
            return ;
        }
        String[] msg = message.split(";");
        if (msg.length != 11) {
            return;
        }

        String[] m = message.split(";");
        MemMetric mem = new MemMetric();
        mem.setAppid(m[1]);
        mem.setTime(Long.parseLong(m[0]));
        mem.setAppName(m[2]);
        mem.setNonHeapInit(Long.parseLong(m[3]));
        mem.setNonHeapUsed(Long.parseLong(m[4]));
        mem.setNonHeapCommitted(Long.parseLong(m[5]));
        mem.setNonHeapMax(Long.parseLong(m[6]));
        mem.setHeapInit(Long.parseLong(m[7]));
        mem.setHeapUsed(Long.parseLong(m[8]));
        mem.setHeapCommitted(Long.parseLong(m[9]));
        mem.setHeapMax(Long.parseLong(m[10]));
        memMetricRepository.save(mem);

    }

    public void saveGcMetrics(String message) {
        if(StringUtils.isEmpty(message)) {
            return ;
        }
        String[] msg = message.split(";");
        if (msg.length != 6) {
            return;
        }

        String[] m = message.split(";");
        long time = Long.parseLong(m[0]);
        String appId = m[1];
        String app = m[2];
        String gcName = m[3];
        long count = Long.parseLong(m[4]);
        long gcTime = Long.parseLong(m[5]);
        GcMetric gc = new GcMetric(appId, time, app, gcName, count, gcTime);
        gcMetricRepository.save(gc);
    }
}
