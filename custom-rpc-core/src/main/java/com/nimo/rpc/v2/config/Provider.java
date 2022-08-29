package com.nimo.rpc.v2.config;

import com.nimo.rpc.v2.URL;

import java.lang.reflect.Method;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Provider<T> {

    private T proxyImpl;

    protected Class<T> clz;

    protected URL url;

    public Provider(T proxyImpl, Class<T> clz, URL url) {
        this.proxyImpl = proxyImpl;
        this.clz = clz;
        this.url = url;
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

    public Method lookupMethod(String methodName) {
        return null;
    }
}
