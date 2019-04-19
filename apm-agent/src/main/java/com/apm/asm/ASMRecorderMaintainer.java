package com.apm.asm;


import com.apm.base.config.MethodThreshold;
import com.apm.core.recorder.AbstractRecorderMaintainer;
import com.apm.core.recorder.Recorders;

public class ASMRecorderMaintainer extends AbstractRecorderMaintainer {

    private static final ASMRecorderMaintainer instance = new ASMRecorderMaintainer();

    public static ASMRecorderMaintainer getInstance() {
        return instance;
    }

    @Override
    public boolean initOther() {
        return true;
    }

    @Override
    public void addRecorder(int methodTagId, MethodThreshold params) {
        for (int i = 0; i < recordersList.size(); ++i) {
            Recorders recorders = recordersList.get(i);
            recorders.setRecorder(methodTagId, createRecorder(methodTagId, params.getTime(), params.getCount()));
        }
    }
}
