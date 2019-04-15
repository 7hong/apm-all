package com.apm.apmserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.apm.apmserver.bean.SearchBean;
import com.apm.apmserver.dao.AtomicData;
import com.apm.apmserver.service.AppService;
import com.apm.apmserver.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 14:19
 * @Description:
 */
@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private AppService appService;

    @Autowired
    private MetricService methodService;

    @Autowired
    private MetricService metricService;



    @GetMapping("/apps")
    public List<String> apps() {
        List<String> apps = appService.apps();
        return apps;
    }


    @GetMapping("/methods")
    public List<String> methods(@RequestParam(value = "app") String app) {
        return appService.methods(app, true);
    }

    @GetMapping("/top")
    public JSONObject top() {
        JSONObject result = new JSONObject();
        List<String> apps = appService.apps();
        List<String> methods = appService.methods("_", true);

        JSONObject tps = metricService.indexTpsMetric();

        Map<Integer, Long> tpsChart = AtomicData.KAFKA_TPS.asMap();
        Set<Map.Entry<Integer, Long>> entries = tpsChart.entrySet();
        Iterator<Map.Entry<Integer, Long>> iterator = entries.iterator();
        List<Integer> x = new ArrayList<>(tpsChart.size());
        List<Long> k_tps = new ArrayList<>(tpsChart.size());
        while(iterator.hasNext()) {
            x.add(iterator.next().getKey());
            k_tps.add(iterator.next().getValue());
        }
        JSONObject kafkaTps = new JSONObject(2);
        kafkaTps.put("time", x);
        kafkaTps.put("tps", k_tps);
        SearchBean search = new SearchBean();
        search.setApp("_");
        search.setStartTime(new Date().getTime() - 60000 * 10);
        search.setStartTime(new Date().getTime());

        result.put("appCount", apps.size());
        result.put("methodCount", methods.size());
        result.put("kafkaTps",kafkaTps);
        result.put("tps", tps);
        return result;

    }



}
