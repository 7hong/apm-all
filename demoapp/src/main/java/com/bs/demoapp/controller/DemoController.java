package com.bs.demoapp.controller;

import com.bs.demoapp.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @Autowired
    private DemoService demoService;

    @GetMapping("/a")
    public String demo1() {
        String re = demoService.demoFun("字符串1", "字符串2");
        return re + System.currentTimeMillis();
    }

    @GetMapping("/b")
    public String demo2() {
        return "demo2";
    }
}
