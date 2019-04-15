package com.apm.apmserver.repositories;

import com.apm.apmserver.bean.cassandra.MemMetric;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface MemMetricRepository extends CassandraRepository<MemMetric> {


    @Query("select * from mem_metric where appname=?0 and time >= ?1 and time <= ?2")
    List<MemMetric> queryMemMetric(String app, long startTime, long endTime);
}
