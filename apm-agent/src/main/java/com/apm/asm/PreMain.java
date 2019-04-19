package com.apm.asm;

import com.apm.asm.aop.ProfilingTransformer;
import com.apm.core.ASMBootstrap;

import java.lang.instrument.Instrumentation;

/**
 * Created by Jiang Qihong on 2018/4/25
 * agent 入口方法
 */
public class PreMain {

    public static void premain(String options, Instrumentation ins) {
        if (ASMBootstrap.getInstance().initial()) {
            ins.addTransformer(new ProfilingTransformer());
        }
    }

}
