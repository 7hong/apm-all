package com.apm.apmserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.apm.apmserver.bean.SearchBean;
import com.apm.apmserver.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/17 14:25
 * @Description:
 */
@RestController
@RequestMapping("/chart")
public class ChartDataController {

    @Autowired
    private MetricService metricService;


    @GetMapping("/method_metric")
    public JSONObject methodMetric(SearchBean searchBean) {

        if (StringUtils.isEmpty(searchBean.getApp())) {
            return null;
        }
        loadTimeTnterval(searchBean);
        JSONObject json = metricService.methodMetric(searchBean);
        return json;
    }


    @GetMapping("/thread_metric")
    public JSONObject threadMetric(SearchBean searchBean) {
        if (StringUtils.isEmpty(searchBean.getApp())) {
            return null;
        }
        loadTimeTnterval(searchBean);
        JSONObject json = metricService.threadMetric(searchBean);
        return json;
    }

    @GetMapping("/gc_metric")
    public JSONObject gcMetric(SearchBean searchBean) {
        if (StringUtils.isEmpty(searchBean.getApp())) {
            return null;
        }
        loadTimeTnterval(searchBean);
        JSONObject json = metricService.gcMetric(searchBean);
        return json;
    }

    @GetMapping("/mem_metric")
    public JSONObject memMetric(SearchBean searchBean) {
        if (StringUtils.isEmpty(searchBean.getApp())) {
            return null;
        }
        loadTimeTnterval(searchBean);
        JSONObject json = metricService.memMetric(searchBean);
        return json;
    }

    @GetMapping("/class_metric")
    public JSONObject classMetric(SearchBean searchBean) {
        if (StringUtils.isEmpty(searchBean.getApp())) {
            return null;
        }
        loadTimeTnterval(searchBean);
        JSONObject json = metricService.classMetric(searchBean);
        return json;
    }


    private void loadTimeTnterval(SearchBean searchBean) {
        if (searchBean.getStartTime() == 0 || searchBean.getEndTime() == 0) {
            searchBean.setStartTime(new Date().getTime() - 3600 * 1000 * 6 * 4);//默认查询六小时
            searchBean.setEndTime(new Date().getTime());
        }
    }
}
