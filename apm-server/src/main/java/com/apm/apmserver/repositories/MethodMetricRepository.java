package com.apm.apmserver.repositories;

import com.apm.apmserver.bean.cassandra.*;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface MethodMetricRepository extends CassandraRepository<MethodMetric> {

    @Query("select * from method_metric where appname=?0 and class_name=?1 and method_name=?2 and time >= ?3 and time <= ?4")
    List<MethodMetric> queryMethodMetric(String AppName,String className,  String methodName, long start, long end);



}
