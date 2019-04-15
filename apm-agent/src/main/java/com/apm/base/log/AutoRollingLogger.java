package com.apm.base.log;

import com.apm.base.util.file.AutoRollingFileWriter;
import com.apm.base.util.file.DailyRollingFileWriter;

public class AutoRollingLogger implements ILogger {

    private final AutoRollingFileWriter writer;

    AutoRollingLogger(String logFile) {
        this.writer = new DailyRollingFileWriter(logFile);
    }

    @Override
    public void log(String msg) {
        writer.write(msg + '\n');
    }

    @Override
    public void logAndFlush(String msg) {
        writer.writeAndFlush(msg + '\n');
    }

    @Override
    public void flushLog() {
        writer.flush();
    }

    @Override
    public void preCloseLog() {
        writer.preCloseFile();
    }

    @Override
    public void closeLog() {
        writer.closeFile(true);
    }

    @Override
    protected void finalize() {
        writer.closeFile(true);
    }
}
