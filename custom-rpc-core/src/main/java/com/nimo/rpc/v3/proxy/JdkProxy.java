package com.nimo.rpc.v3.proxy;

import com.nimo.rpc.v3.remoting.NettyClient;

import java.lang.reflect.Proxy;

public class JdkProxy {

    public <T> T getProxy(NettyClient client, Class interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaces}, new InvokerInvocationHandler(client));
    }

}
