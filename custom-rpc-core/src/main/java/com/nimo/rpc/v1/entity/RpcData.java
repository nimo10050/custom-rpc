package com.nimo.rpc.v1.entity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 远程调用对象
 */
public class RpcData implements Serializable {

    // id
    private String serviceName;
    // 服务地址
    private String host;
    // 端口
    private Integer port;
    // 类对象
    private Class cls;
    // 方法名
    private String methodName;
    // 方法参数
    private Object[] args;
    // 参数类型
    private Class[] parameterType;
    // 服务实现类名
    private String serviceImplQualifyName;

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


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImplQualifyName() {
        return serviceImplQualifyName;
    }

    public void setServiceImplQualifyName(String serviceImplQualifyName) {
        this.serviceImplQualifyName = serviceImplQualifyName;
    }

    @Override
    public String toString() {
        return "RpcData{" +
                "serviceName='" + serviceName + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", cls=" + cls +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", parameterType=" + Arrays.toString(parameterType) +
                ", serviceImplQualifyName='" + serviceImplQualifyName + '\'' +
                '}';
    }
}
