package com.apm.core;


import com.apm.base.MethodTag;


public abstract class AbstractMethodTagMaintainer {

    public abstract int addMethodTag(MethodTag methodTag);

    public abstract MethodTag getMethodTag(int methodId);

    public abstract int getMethodTagCount();

}
