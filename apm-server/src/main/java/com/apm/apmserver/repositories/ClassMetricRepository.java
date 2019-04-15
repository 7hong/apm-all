package com.apm.apmserver.repositories;

import com.apm.apmserver.bean.cassandra.ClassMetric;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface ClassMetricRepository extends CassandraRepository<ClassMetric> {


    @Query("select * from class_metric where appname=?0 and time >= ?1 and time <= ?2")
    List<ClassMetric> queryClassMetric(String app, long startTime, long endTime);
}
