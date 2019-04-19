package com.apm.base;


public class MethodTag {

    /**
     * 类名称：eg. com.bs.controller
     */
    private final String className;

    /**
     * 方法名：eg. getName
     */
    private final String methodName;

    /**
     * 参数列表：eg (String, String)
     */
    private final String methodParamDesc;

    /**
     * 全名：com.bs.controller.getName(String, String)
     */
    private final String fullMethodName;

    private MethodTag(String className, String methodName, String methodParamDesc) {
        this.className = className;
        this.methodName = methodName;
        this.methodParamDesc = methodParamDesc;
        this.fullMethodName = className + "." + methodName + methodParamDesc;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodParamDesc() {
        return methodParamDesc;
    }

    public String getFullMethodName() {
        return fullMethodName;
    }

    @Override
    public String toString() {
        return "MethodTag{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodParamDesc='" + methodParamDesc + '\'' +
                ", fullMethodName='" + fullMethodName + '\'' +
                '}';
    }

    public static MethodTag getInstance(String className, String methodName, String methodParamDesc) {
        return new MethodTag(className, methodName, methodParamDesc);
    }
}
