package com.apm.asm.aop;

import com.apm.asm.ASMRecorderMaintainer;
import com.apm.base.util.Logger;
import com.apm.core.MethodTagMaintainer;
import com.apm.core.recorder.Recorder;

/**
 * Created by Jiang Qihong on 2018/4/15
 */
public final class ProfilingAspect {

    private static volatile ASMRecorderMaintainer recorderMaintainer;

    private static volatile boolean running = false;

    public static void profiling(long startNanos, int methodTagId) {
        try {
            if (!running) {
                Logger.warn("ProfilingAspect.doProfiling(): methodTagId=" + methodTagId + ", methodTag=" + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ", startNanos: " + startNanos + ", IGNORED!!!");
                return;
            }

            Recorder recorder = recorderMaintainer.getRecorder(methodTagId);
            if (recorder == null) {
                Logger.warn("ProfilingAspect.doProfiling(): methodTagId=" + methodTagId + ", methodTag=" + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ", startNanos: " + startNanos + ", recorder is null IGNORED!!!");
                return;
            }

            long stopNanos = System.nanoTime();
            recorder.recordTime(startNanos, stopNanos);
        } catch (Exception e) {
            Logger.error("ProfilingAspect.doProfiling(" + startNanos + ", " + methodTagId + ", " + MethodTagMaintainer.getInstance().getMethodTag(methodTagId) + ")", e);
        }
    }

    public static void setRecorderMaintainer(ASMRecorderMaintainer maintainer) {
        recorderMaintainer = maintainer;
    }

    public static void setRunning(boolean run) {
        running = run;
    }
}
