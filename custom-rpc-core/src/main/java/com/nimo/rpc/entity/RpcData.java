package com.nimo.rpc.entity;

import java.io.Serializable;

public class RpcData implements Serializable {

    // 服务地址
    private String host;
    // 端口
    private Integer port;
    // 实现类名字
    private String implClassQualifyName;
    // 类对象
    private Class cls;
    // 方法名
    private String methodName;
    // 方法参数
    private Object[] args;
    // 参数类型
    private Class[] parameterType;

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getImplClassQualifyName() {
        return implClassQualifyName;
    }

    public void setImplClassQualifyName(String implClassQualifyName) {
        this.implClassQualifyName = implClassQualifyName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Class[] getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class[] parameterType) {
        this.parameterType = parameterType;
    }
}
