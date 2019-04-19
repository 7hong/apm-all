package com.apm.core;

import com.apm.asm.ASMRecorderMaintainer;
import com.apm.asm.aop.ProfilingAspect;
import com.apm.base.Scheduler;
import com.apm.base.config.ApmConfig;
import com.apm.base.config.MethodThreshold;
import com.apm.base.config.PropertiesUtil;
import com.apm.base.config.ProfilingFilter;
import com.apm.base.constant.PropertyKeys;
import com.apm.base.constant.PropertyValues;
import com.apm.base.metric.processor.*;
import com.apm.base.util.ExecutorManager;
import com.apm.base.util.IOUtils;
import com.apm.base.util.Logger;
import com.apm.core.recorder.AbstractRecorderMaintainer;
import com.apm.core.scheduler.JvmMetricsScheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class ASMBootstrap {

    private static final ASMBootstrap instance = new ASMBootstrap();

    private AbstractRecorderMaintainer maintainer;

    private ASMBootstrap() {
        //empty
    }

    public static ASMBootstrap getInstance() {
        return instance;
    }

    public final boolean initial() {
        try {
            if (!doInitial()) {
                Logger.error("AbstractBootstrap doInitial() FAILURE!!!");
                return false;
            }

            printBannerText();
            Logger.info("AbstractBootstrap doInitial() SUCCESS!!!");
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initial()", e);
        }
        return false;
    }

    private boolean doInitial() {

        //加载配置文件
        if (!initProperties()) {
            Logger.error("AbstractBootstrap initProperties() FAILURE!!!");
            return false;
        }

        //读取配置属性
        if (!initProfilingConfig()) {
            Logger.error("AbstractBootstrap initProfilingConfig() FAILURE!!!");
            return false;
        }

        //设置日志级别
        if (!initLogger()) {
            Logger.error("AbstractBootstrap initLogger() FAILURE!!!");
            return false;
        }

        //设置排除和过滤的包
        if (!initPackageFilter()) {
            Logger.error("AbstractBootstrap initPackageFilter() FAILURE!!!");
            return false;
        }

        //排除的classLoader
        if (!initClassLoaderFilter()) {
            Logger.error("AbstractBootstrap initClassLoaderFilter() FAILURE!!!");
            return false;
        }

        //排除的方法
        if (!initMethodFilter()) {
            Logger.error("AbstractBootstrap initMethodFilter() FAILURE!!!");
            return false;
        }

        //加载ProfilingParamsFile配置文件
        if (!initProfilingParams()) {
            Logger.error("AbstractBootstrap initProfilingParams() FAILURE!!!");
            return false;
        }

        //初始化RecorderMaintainer，初始化后台计算线程池
        if (!initRecorderMaintainer()) {
            Logger.error("AbstractBootstrap initRecorderMaintainer() FAILURE!!!");
            return false;
        }

        //添加shutdownHook
        if (!initShutDownHook()) {
            Logger.error("AbstractBootstrap initShutDownHook() FAILURE!!!");
            return false;
        }

        //初始化jvm、method数据采集任务
        if (!initScheduler()) {
            Logger.error("AbstractBootstrap initScheduler() FAILURE!!!");
            return false;
        }

        if (!initOther()) {
            Logger.error("AbstractBootstrap initOther() FAILURE!!!");
            return false;
        }
        return true;
    }

    private boolean initProperties() {
        InputStream in = null;
        try {
            String path = System.getProperty(PropertyKeys.PRO_FILE_NAME);
            in = new FileInputStream(path);

            Properties properties = new Properties();
            properties.load(in);
            return PropertiesUtil.initial(properties);
        } catch (IOException e) {
            Logger.error("AbstractBootstrap.initProperties()", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return false;
    }

    private boolean initProfilingConfig() {
        try {
            ApmConfig config = ApmConfig.getInstance();
            String appName = PropertiesUtil.getStr(PropertyKeys.APP_NAME);
            if (appName == null || appName.isEmpty()) {
                throw new IllegalArgumentException("AppName isEmpty");
            }
            config.setAppName(appName);
            String appid = PropertiesUtil.getStr(PropertyKeys.APP_ID);
            if (appid == null || "".equals(appid)) {
                throw new IllegalArgumentException("appId isEmpty");
            }
            config.setAppId(appid);
            config.setKafka(PropertiesUtil.getStr(PropertyKeys.KAFKA_SERVER, "127.0.0.1:9092"));
            config.setMethodMetricsFile(PropertiesUtil.getStr(PropertyKeys.METHOD_METRICS_FILE, "./apmdata/method_metrics.log"));
            config.setClassMetricsFile(PropertiesUtil.getStr(PropertyKeys.CLASS_METRICS_FILE, "./apmdata/class_metrics.log"));
            config.setGcMetricsFile(PropertiesUtil.getStr(PropertyKeys.GC_METRICS_FILE, "./apmdata/gc_metrics.log"));
            config.setMemoryMetricsFile(PropertiesUtil.getStr(PropertyKeys.MEM_METRICS_FILE, "./apmdata/memory_metrics.log"));
            config.setThreadMetricsFile(PropertiesUtil.getStr(PropertyKeys.THREAD_METRICS_FILE, "./apmdata/thread_metrics.log"));

            config.setBackupRecorderCount(PropertiesUtil.getInt(PropertyKeys.BACKUP_RECORDERS_COUNT, 1));
            config.setMilliTimeSlice(PropertiesUtil.getLong(PropertyKeys.MILL_TIME_SLICE, PropertyValues.DEFAULT_TIME_SLICE));

            config.setShowMethodParams(PropertiesUtil.getBoolean(PropertyKeys.SHOW_METHOD_PARAMS, false));

            String includePackages = PropertiesUtil.getStr(PropertyKeys.FILTER_INCLUDE_PACKAGES, "");
            if (includePackages == null) {
                throw new IllegalArgumentException("IncludePackages is required!!!");
            }
            config.setIncludePackages(includePackages);

            config.setExcludePackages(PropertiesUtil.getStr(PropertyKeys.FILTER_EXCLUDE_PACKAGES, ""));
            config.setLogLevel(PropertiesUtil.getStr(PropertyKeys.LOG_LEVEL, "info"));
            config.setExcludeMethods(PropertiesUtil.getStr(PropertyKeys.FILTER_EXCLUDE_METHODS, ""));
            config.setExcludePrivateMethod(PropertiesUtil.getBoolean(PropertyKeys.EXCLUDE_PRIVATE_METHODS, true));
            config.setExcludeClassLoaders(PropertiesUtil.getStr(PropertyKeys.FILTER_INCLUDE_CLASS_LOADERS, ""));
            config.setProfilingParamsFile(PropertiesUtil.getStr(PropertyKeys.PROFILING_PARAMS_FILE_NAME, ""));
            config.setMethodThreshold(new MethodThreshold(PropertiesUtil.getInt(PropertyKeys.PROFILING_TIME_THRESHOLD, 1000), PropertiesUtil.getInt(PropertyKeys.PROFILING_OUT_THRESHOLD_COUNT, 16)));
            Logger.info("load config success: " + config.toString());
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initProfilingConfig()", e);
        }
        return false;
    }

    private boolean initLogger() {
        try {
            Logger.setDebugEnable("debug".equalsIgnoreCase(ApmConfig.getInstance().getLogLevel()));
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initLogger()", e);
        }
        return false;
    }

    private boolean initPackageFilter() {
        try {
            String includePackages = ApmConfig.getInstance().getIncludePackages();
            String[] includeArr = includePackages.split(PropertyValues.FILTER_SEPARATOR);
            if (includeArr.length > 0) {
                for (String pkg : includeArr) {
                    ProfilingFilter.addIncludePackage(pkg);
                }
            }

            String excludePackages = ApmConfig.getInstance().getExcludePackages();
            String[] excludeArr = excludePackages.split(PropertyValues.FILTER_SEPARATOR);
            if (excludeArr.length > 0) {
                for (String pkg : excludeArr) {
                    ProfilingFilter.addExcludePackage(pkg);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initPackageFilter()", e);
        }
        return false;
    }

    private boolean initClassLoaderFilter() {
        try {
            String excludeClassLoaders = ApmConfig.getInstance().getExcludeClassLoaders();
            String[] excludeArr = excludeClassLoaders.split(PropertyValues.FILTER_SEPARATOR);
            if (excludeArr.length > 0) {
                for (String classLoader : excludeArr) {
                    ProfilingFilter.addExcludeClassLoader(classLoader);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initClassLoaderFilter()", e);
        }
        return false;
    }

    private boolean initMethodFilter() {
        try {
            String includePackages = ApmConfig.getInstance().getExcludeMethods();
            String[] excludeArr = includePackages.split(PropertyValues.FILTER_SEPARATOR);
            if (excludeArr.length > 0) {
                for (String method : excludeArr) {
                    ProfilingFilter.addExcludeMethods(method);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initMethodFilter()", e);
        }
        return false;
    }

    private boolean initProfilingParams() {
        InputStream in = null;
        try {
            ApmConfig config = ApmConfig.getInstance();
            String profilingParamFile = config.getProfilingParamsFile();
            if (profilingParamFile == null || profilingParamFile.isEmpty()) {
                Logger.warn("profilingParamFile is empty!");
                return true;
            }

            in = new FileInputStream(profilingParamFile);
            Properties properties = new Properties();
            properties.load(in);

            Set<String> keys = properties.stringPropertyNames();
            for (String key : keys) {
                String value = properties.getProperty(key);
                if (value == null) {
                    continue;
                }

                String[] strings = value.split(":");
                if (strings.length != 2) {
                    continue;
                }

                int timeThreshold = getInt(strings[0].trim(), 500);
                int outThresholdCount = getInt(strings[1].trim(), 50);
                config.addProfilingParam(key.replace('.', '/'), timeThreshold, outThresholdCount);
            }

            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initProfilingParams()", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return false;
    }

    private int getInt(String str, int defaultValue) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.getInt(" + str + ")", e);
        }
        return defaultValue;
    }

    private boolean initRecorderMaintainer() {
        int backupRecorderCount = ApmConfig.getInstance().getBackupRecorderCount();

        maintainer = ASMRecorderMaintainer.getInstance();
        MethodMetricsProcessor processor = MetricsProcessorFactory.getMethodMetricsProcessor();
        boolean initial = maintainer.initial(processor, backupRecorderCount);
        return initial;
    }

    private boolean initShutDownHook() {
        try {
            //在jvm推出之前执行，关闭所有线程池
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    Logger.info("ENTER ShutdownHook...");
                    try {
                        ExecutorManager.stopAll(6, TimeUnit.SECONDS);
                    } finally {
                        Logger.info("EXIT ShutdownHook...");
                    }
                }
            }));
            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initShutDownHook()", e);
        }
        return false;
    }

    private boolean initScheduler() {
        try {

            JvmMetricScheduler.initScheduleTask(createJVMMetricsScheduler(), 10000);

            MethodScheduler.initScheduleTask(maintainer, 1000);

            return true;
        } catch (Exception e) {
            Logger.error("AbstractBootstrap.initScheduler()", e);
        }
        return false;
    }

    private Scheduler createJVMMetricsScheduler() {
        JvmClassMetricsProcessor classProcessor = MetricsProcessorFactory.getClassMetricsProcessor();
        JvmGCMetricsProcessor gcProcessor = MetricsProcessorFactory.getGCMetricsProcessor();
        JvmMemoryMetricsProcessor memoryProcessor = MetricsProcessorFactory.getMemoryMetricsProcessor();
        JvmThreadMetricsProcessor threadProcessor = MetricsProcessorFactory.getThreadMetricsProcessor();
        return new JvmMetricsScheduler(classProcessor, gcProcessor, memoryProcessor, threadProcessor);
    }


    public boolean initOther() {
        return initProfilerAspect();
    }

    private boolean initProfilerAspect() {
        try {
            ProfilingAspect.setRecorderMaintainer((ASMRecorderMaintainer) maintainer);
            ProfilingAspect.setRunning(true);
            return true;
        } catch (Exception e) {
            Logger.error("ASMBootstrap.initProfilerAspect()", e);
        }
        return false;
    }

    private void printBannerText() {
        String logo = "======探针加载成功=====";
        Logger.info(logo);
    }

}
