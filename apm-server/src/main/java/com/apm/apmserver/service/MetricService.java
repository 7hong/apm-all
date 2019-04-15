package com.apm.apmserver.service;

import com.alibaba.fastjson.JSONObject;
import com.apm.apmserver.bean.SearchBean;
import com.apm.apmserver.bean.cassandra.*;
import com.apm.apmserver.dao.RedisDao;
import com.apm.apmserver.repositories.*;
import jnr.x86asm.Mem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 14:50
 * @Description:
 */

@Service
public class MetricService {

    @Autowired
    private MethodMetricRepository methodMetricRepository;

    @Autowired
    private ThreadMetricRepository threadMetricRepository;

    @Autowired
    private GcMetricRepository gcMetricRepository;

    @Autowired
    private ClassMetricRepository classMetricRepository;

    @Autowired
    private MemMetricRepository memMetricRepository;


    public JSONObject indexTpsMetric() {
        SearchBean search = new SearchBean();
        search.setStartTime(new Date().getTime() - 60000 * 10);
        search.setStartTime(new Date().getTime());
        List<MethodMetric> list = methodMetricRepository.queryMethodMetric("_", "", "_",search.getStartTime(),search.getEndTime());

        int size = list.size();
        List<Long>  x = new ArrayList<>(size);
        List<Integer> rps = new ArrayList<>(size);
        if (size == 0) {
            MethodMetric methodMetric = new MethodMetric();
            methodMetric.setTime(search.getEndTime());
            list.add(methodMetric);
        }
        for (MethodMetric mm : list) {
            rps.add(mm.getRps());
        }

        JSONObject json = new JSONObject(5);
        json.put("x",x);
        json.put("rps", rps);
        return json;

    }


    public JSONObject methodMetric(SearchBean search) {
        List<MethodMetric> list = methodMetricRepository.queryMethodMetric(search.getApp(), "", search.getMethod(),search.getStartTime(),search.getEndTime());

        int size = list.size();
        if (size == 0) {
            MethodMetric methodMetric = new MethodMetric();
            methodMetric.setTime(search.getEndTime());
            list.add(methodMetric);
        }
        List<Long>  x = new ArrayList<>(size);
        List<Double> avg = new ArrayList<>(size);
        List<Integer> rps = new ArrayList<>(size);
        List<Integer> tp90 = new ArrayList<>(size);
        List<Integer> tp99 = new ArrayList<>(size);
        for (MethodMetric mm : list) {
            x.add(mm.getTime());
            avg.add(mm.getAvg());
            rps.add(mm.getRps());
            tp90.add(mm.getTp90());
            tp99.add(mm.getTp99());
        }
        JSONObject json = new JSONObject(5);
        json.put("x",x);
        json.put("avg", avg);
        json.put("rps", rps);
        json.put("tp90", tp90);
        json.put("tp99", tp99);
        return json;

    }

    public JSONObject threadMetric(SearchBean search) {

        List<ThreadMetric> list = threadMetricRepository.queryThreadMetric(search.getApp(),search.getStartTime(),search.getEndTime());

        int size = list.size();
        if (size == 0) {
            ThreadMetric t = new ThreadMetric();
            t.setTime(search.getEndTime());
            list.add(t);
        }
        List<Long> time = new ArrayList<>(size);
        List<Long> totalStarted = new ArrayList<>(size);
        List<Integer> active = new ArrayList<>(size);
        List<Integer> peak = new ArrayList<>(size);
        List<Integer> daemon = new ArrayList<>(size);
        List<Integer> news = new ArrayList<>(size);
        List<Integer> runnable = new ArrayList<>(size);
        List<Integer> blocked = new ArrayList<>(size);
        List<Integer> waiting = new ArrayList<>(size);
        List<Integer> terminated = new ArrayList<>(size);
        List<Integer> timedWaiting = new ArrayList<>(size);
        list.forEach(x -> {
            time.add(x.getTime());
            totalStarted.add(x.getTotalStarted());
            active.add(x.getActive());
            peak.add(x.getPeak());
            daemon.add(x.getDaemon());
            news.add(x.getDaemon());
            runnable.add(x.getRunnable());
            blocked.add(x.getBlocked());
            waiting.add(x.getWaiting());
            terminated.add(x.getTerminated());
            timedWaiting.add(x.getTimedWaiting());
        });
        JSONObject json = new JSONObject(11);
        json.put("time",time);
        json.put("total", totalStarted);
        json.put("active", active);
        json.put("peak", peak);
        json.put("daemon", daemon);
        json.put("news", news);
        json.put("runnable", runnable);
        json.put("blocked", blocked);
        json.put("waiting", waiting);
        json.put("terminated", terminated);
        json.put("timedWaiting", timedWaiting);

        return json;
    }


    public JSONObject gcMetric(SearchBean search) {
        List<GcMetric> list = gcMetricRepository.queryGcMetric(search.getApp(),search.getStartTime(),search.getEndTime());

        int size = list.size();
        if (size == 0) {
            GcMetric g = new GcMetric();
            g.setTime(search.getEndTime());
            list.add(g);
        }
        List<Long> time = new ArrayList<>(size);
        List<String> gcName = new ArrayList<>(size);
        List<Long> collectCount = new ArrayList<>(size);
        List<Long> collectTime = new ArrayList<>(size);
        list.forEach(x -> {
            time.add(x.getTime());
            gcName.add(x.getGcName());
            collectCount.add(x.getCollectCount());
            collectTime.add(x.getCollectTime());
        });
        JSONObject json = new JSONObject(4);
        json.put("time", time);
        json.put("gcName", gcName);
        json.put("gcTime", collectTime);
        json.put("gcCount", collectCount);

        return json;
    }


    public JSONObject memMetric(SearchBean search) {
        List<MemMetric> list = memMetricRepository.queryMemMetric(search.getApp(),search.getStartTime(),search.getEndTime());

        int size = list.size();
        if (size == 0) {
            MemMetric m = new MemMetric();
            m.setTime(search.getEndTime());
            list.add(m);
        }
        List<Long> time = new ArrayList<>(size);
        List<Long> nonHeapInit = new ArrayList<>(size);
        List<Long> nonHeapUsed = new ArrayList<>(size);
        List<Long> nonHeapCommitted = new ArrayList<>(size);
        List<Long> nonHeapMax = new ArrayList<>(size);
        List<Long> heapInit = new ArrayList<>(size);
        List<Long> heapUsed = new ArrayList<>(size);
        List<Long> heapCommitted = new ArrayList<>(size);
        List<Long> heapMax = new ArrayList<>(size);
        list.forEach(x -> {
            time.add(x.getTime());
            nonHeapInit.add(x.getHeapInit());
            nonHeapUsed.add(x.getHeapUsed());
            nonHeapCommitted.add(x.getNonHeapCommitted());
            nonHeapMax.add(x.getNonHeapMax());
            heapInit.add(x.getHeapInit());
            heapUsed.add(x.getHeapUsed());
            heapCommitted.add(x.getHeapCommitted());
            heapMax.add(x.getHeapMax());
        });
        JSONObject json = new JSONObject(9);
        json.put("time", time);
        json.put("nonHeapInit", nonHeapInit);
        json.put("nonHeapUsed", nonHeapUsed);
        json.put("nonHeapCommitted", nonHeapCommitted);
        json.put("nonHeapMax", nonHeapMax);
        json.put("heapInit", heapInit);
        json.put("heapUsed", heapUsed);
        json.put("heapCommitted", heapCommitted);
        json.put("heapMax", heapMax);
        return json;
    }


    public JSONObject classMetric(SearchBean search) {
        List<ClassMetric> list = classMetricRepository.queryClassMetric(search.getApp(),search.getStartTime(),search.getEndTime());

        int size = list.size();
        if (size == 0) {
            ClassMetric c = new ClassMetric();
            c.setTime(search.getEndTime());
            list.add(c);
        }

        List<Long> time = new ArrayList<>(size);
        List<Long> total = new ArrayList<>(size);
        List<Long> loaded = new ArrayList<>(size);
        List<Long> unloaded = new ArrayList<>(size);
        list.forEach(x -> {
            time.add(x.getTime());
            total.add(x.getTotal());
            loaded.add(x.getLoaded());
            unloaded.add(x.getUnloaded());
        });
        JSONObject json = new JSONObject(4);
        json.put("time", time);
        json.put("total", total);
        json.put("loaded", loaded);
        json.put("unloaded", unloaded);
        return json;
    }

}
