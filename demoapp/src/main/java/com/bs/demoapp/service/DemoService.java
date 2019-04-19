package com.bs.demoapp.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {


    public String demoFun(String a, String b) {

        return a + b;
    }
}
