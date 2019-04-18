package com.apm.apmserver.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisData {

    public static Set<String> apps = new HashSet<>();

    public static Map<String, Set<String>> methods = new HashMap<>();

    private static RedisData INSTANCE = new RedisData();

}
