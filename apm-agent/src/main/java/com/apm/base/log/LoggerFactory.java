package com.apm.base.log;

import com.apm.base.config.ApmConfig;
import com.apm.base.constant.PropertyValues;

import java.util.HashMap;
import java.util.Map;

public final class LoggerFactory {

    private static final Map<String, ILogger> LOGGER_MAP = new HashMap<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                for (ILogger writer : LOGGER_MAP.values()) {
                    writer.preCloseLog();
                }

                for (ILogger writer : LOGGER_MAP.values()) {
                    writer.closeLog();
                }
            }
        }));
    }

    public static synchronized ILogger getLogger(String logFile) {
        logFile = logFile.trim();
        ILogger logger = LOGGER_MAP.get(logFile);
        if (logger != null) {
            return logger;
        }

        logger = new AutoRollingLogger(logFile);
        LOGGER_MAP.put(logFile, logger);
        return logger;
    }
}
