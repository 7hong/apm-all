package com.apm.asm;


import com.apm.asm.aop.ProfilingAspect;
import com.apm.base.config.ApmConfig;
import com.apm.base.util.Logger;
import com.apm.core.AbstractBootstrap;
import com.apm.core.recorder.AbstractRecorderMaintainer;

/**
 * Created by Jiang Qihong on 2018/4/19
 */
public class ASMBootstrap extends AbstractBootstrap {

    private static final ASMBootstrap instance = new ASMBootstrap();

    private ASMBootstrap() {
        //empty
    }

    public static ASMBootstrap getInstance() {
        return instance;
    }

    @Override
    public AbstractRecorderMaintainer doInitRecorderMaintainer() {
        int backupRecorderCount = ApmConfig.getInstance().getBackupRecorderCount();

        ASMRecorderMaintainer maintainer = ASMRecorderMaintainer.getInstance();
        if (maintainer.initial(processor, backupRecorderCount)) {
            return maintainer;
        }
        return null;
    }

    @Override
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

}
