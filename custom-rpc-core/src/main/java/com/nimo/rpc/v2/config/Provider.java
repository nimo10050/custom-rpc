package com.nimo.rpc.v2.config;

import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.model.RpcRequest;
import com.nimo.rpc.v2.model.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Method;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Provider<T> {

    private T proxyImpl;

    private Class<T> clz;

    private URL url;

    private Map<String, Method> methodMap = new HashMap<String, Method>();

    public Provider(T proxyImpl, Class<T> clz, URL url) {
        this.proxyImpl = proxyImpl;
        this.clz = clz;
        this.url = url;
        initMethodMap(this.clz);
    }

    public RpcResponse invoke(RpcRequest request) {
        Method method = lookupMethod(request.getMethodName());
        RpcResponse response = new RpcResponse();
        try {
            Object result = method.invoke(getProxyImpl());
            response.setResult(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initMethodMap(Class<T> clz) {
        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            methodMap.put(methodName, method);
        }
    }

    public Method lookupMethod(String methodName) {
        Method method = methodMap.get(methodName);
        if (method == null) {
            throw new RuntimeException("can not find className: + " + clz.getName() + " methodName: " + methodName);
        }
        return method;
    }

    public T getProxyImpl() {
        return proxyImpl;
    }

    public void setProxyImpl(T proxyImpl) {
        this.proxyImpl = proxyImpl;
    }

    public Class<T> getClz() {
        return clz;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}
