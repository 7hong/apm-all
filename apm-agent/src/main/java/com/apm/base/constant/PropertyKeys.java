package com.apm.base.constant;

/**
 * @Auther: Jiang Qihong
 * @Date: 2019/3/9 00:11
 * @Description: 常量
 */
public interface PropertyKeys {

    String PRO_FILE_NAME = "config";

    String APP_NAME = "AppName";

    String APP_ID = "appId";

    String KAFKA_SERVER = "kafka";

    /**
     * 配置各个Metrics日志的文件路径
     */
    String METHOD_METRICS_FILE = "MethodMetricsFile";

    String CLASS_METRICS_FILE = "ClassMetricsFile";

    String GC_METRICS_FILE = "GCMetricsFile";

    String MEM_METRICS_FILE = "MemMetricsFile";

    String THREAD_METRICS_FILE = "ThreadMetricsFile";

    /**
     * 配置备份Recorders的数量
     */
    String BACKUP_RECORDERS_COUNT = "BackupRecordersCount";

    /**
     * 上报的时间片大小
     */
    String MILL_TIME_SLICE = "MillTimeSlice";


    /**
     * 是否展示方法参数类型
     */
    String SHOW_METHOD_PARAMS = "ShowMethodParams";

    String FILTER_INCLUDE_PACKAGES = "IncludePackages";

    String FILTER_EXCLUDE_PACKAGES = "ExcludePackages";

    String LOG_LEVEL = "LogLevel";

    String FILTER_EXCLUDE_METHODS = "ExcludeMethods";

    String EXCLUDE_PRIVATE_METHODS = "ExcludePrivateMethod";

    String FILTER_INCLUDE_CLASS_LOADERS = "ExcludeClassLoaders";

    String PROFILING_PARAMS_FILE_NAME = "ProfilingParamsFile";

    String PROFILING_TIME_THRESHOLD = "ProfilingTimeThreshold";

    String PROFILING_OUT_THRESHOLD_COUNT = "ProfilingOutThresholdCount";

}
