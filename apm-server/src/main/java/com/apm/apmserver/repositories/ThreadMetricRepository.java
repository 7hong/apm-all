package com.apm.apmserver.repositories;

import com.apm.apmserver.bean.cassandra.ThreadMetric;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface ThreadMetricRepository extends CassandraRepository<ThreadMetric> {


    @Query("select * from thread_metric where appname=?0 and time >= ?1 and time <= ?2")
    List<ThreadMetric> queryThreadMetric(String app, long startTime, long endTime);
}
