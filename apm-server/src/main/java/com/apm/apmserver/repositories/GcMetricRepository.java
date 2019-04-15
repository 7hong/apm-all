package com.apm.apmserver.repositories;

import com.apm.apmserver.bean.cassandra.GcMetric;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface GcMetricRepository extends CassandraRepository<GcMetric> {



    @Query("select * from gc_metric where appname=?0 and time >= ?1 and time <= ?2")
    List<GcMetric> queryGcMetric(String app, long startTime, long endTime);
}
