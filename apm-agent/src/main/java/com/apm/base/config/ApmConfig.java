package com.apm.base.config;

import com.apm.base.util.MapUtils;

import java.util.Map;

/**
 * Created by Jiang Qihong on 2018/5/12
 * 单例
 */
public class ApmConfig {

    private static final ApmConfig instance = new ApmConfig();

    private String appName;

    private String appId;

    private String kafka;

    private String methodMetricsFile;

    private String classMetricsFile;

    private String gcMetricsFile;

    private String memoryMetricsFile;

    private String threadMetricsFile;

    private int backupRecorderCount;

    private long milliTimeSlice;

    private String excludeClassLoaders;

    private String includePackages;

    private String excludePackages;

    private boolean showMethodParams;

    private String LogLevel;

    private String excludeMethods;

    private boolean excludePrivateMethod;

    private String profilingParamsFile;

    private MethodThreshold methodThreshold;

    private Map<String, MethodThreshold> profilingParamsMap = MapUtils.createHashMap(100);


    /**
     * singleton pattern
     */
    public static ApmConfig getInstance() {
        return instance;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKafka() {
        return kafka;
    }

    public void setKafka(String kafka) {
        this.kafka = kafka;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMethodMetricsFile() {
        return methodMetricsFile;
    }

    public void setMethodMetricsFile(String methodMetricsFile) {
        this.methodMetricsFile = methodMetricsFile;
    }

    public String getClassMetricsFile() {
        return classMetricsFile;
    }

    public void setClassMetricsFile(String classMetricsFile) {
        this.classMetricsFile = classMetricsFile;
    }

    public String getGcMetricsFile() {
        return gcMetricsFile;
    }

    public void setGcMetricsFile(String gcMetricsFile) {
        this.gcMetricsFile = gcMetricsFile;
    }

    public String getMemoryMetricsFile() {
        return memoryMetricsFile;
    }

    public void setMemoryMetricsFile(String memoryMetricsFile) {
        this.memoryMetricsFile = memoryMetricsFile;
    }

    public String getThreadMetricsFile() {
        return threadMetricsFile;
    }

    public void setThreadMetricsFile(String threadMetricsFile) {
        this.threadMetricsFile = threadMetricsFile;
    }

    public int getBackupRecorderCount() {
        return backupRecorderCount;
    }

    public void setBackupRecorderCount(int backupRecorderCount) {
        this.backupRecorderCount = backupRecorderCount;
    }

    public long getMilliTimeSlice() {
        return milliTimeSlice;
    }

    public void setMilliTimeSlice(long milliTimeSlice) {
        this.milliTimeSlice = milliTimeSlice;
    }

    public boolean isShowMethodParams() {
        return showMethodParams;
    }

    public void setShowMethodParams(boolean showMethodParams) {
        this.showMethodParams = showMethodParams;
    }

    public String getIncludePackages() {
        return includePackages;
    }

    public void setIncludePackages(String includePackages) {
        this.includePackages = includePackages;
    }

    public String getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(String excludePackages) {
        this.excludePackages = excludePackages;
    }

    public String getExcludeClassLoaders() {
        return excludeClassLoaders;
    }

    public void setExcludeClassLoaders(String excludeClassLoaders) {
        this.excludeClassLoaders = excludeClassLoaders;
    }

    public String getLogLevel() {
        return LogLevel;
    }

    public void setLogLevel(String logLevel) {
        LogLevel = logLevel;
    }

    public String getExcludeMethods() {
        return excludeMethods;
    }

    public void setExcludeMethods(String excludeMethods) {
        this.excludeMethods = excludeMethods;
    }

    public boolean isExcludePrivateMethod() {
        return excludePrivateMethod;
    }

    public void setExcludePrivateMethod(boolean excludePrivateMethod) {
        this.excludePrivateMethod = excludePrivateMethod;
    }

    public String getProfilingParamsFile() {
        return profilingParamsFile;
    }

    public void setProfilingParamsFile(String profilingParamsFile) {
        this.profilingParamsFile = profilingParamsFile;
    }


    public MethodThreshold getMethodThreshold() {
        return methodThreshold;
    }

    public void setMethodThreshold(MethodThreshold methodThreshold) {
        this.methodThreshold = methodThreshold;
    }

    public void addProfilingParam(String methodName, int timeThreshold, int outThresholdCount) {
        profilingParamsMap.put(methodName, MethodThreshold.of(timeThreshold, outThresholdCount));
    }

    public MethodThreshold getProfilingParam(String methodName) {
        MethodThreshold params = profilingParamsMap.get(methodName);
        if (params != null) {
            return params;
        }

        return methodThreshold;
    }

    @Override
    public String toString() {
        return "ApmConfig{" +
                "appName='" + appName + '\'' +
                ", methodMetricsFile='" + methodMetricsFile + '\'' +
                ", classMetricsFile='" + classMetricsFile + '\'' +
                ", gcMetricsFile='" + gcMetricsFile + '\'' +
                ", memoryMetricsFile='" + memoryMetricsFile + '\'' +
                ", threadMetricsFile='" + threadMetricsFile + '\'' +
                ", backupRecorderCount=" + backupRecorderCount +
                ", milliTimeSlice=" + milliTimeSlice +
                ", excludeClassLoaders='" + excludeClassLoaders + '\'' +
                ", includePackages='" + includePackages + '\'' +
                ", excludePackages='" + excludePackages + '\'' +
                ", showMethodParams=" + showMethodParams +
                ", LogLevel=" + LogLevel +
                ", excludeMethods='" + excludeMethods + '\'' +
                ", excludePrivateMethod=" + excludePrivateMethod +
                ", profilingParamsFile='" + profilingParamsFile + '\'' +
                ", MethodThreshold=" + methodThreshold +
                ", profilingParamsMap=" + profilingParamsMap +
                '}';
    }
}
