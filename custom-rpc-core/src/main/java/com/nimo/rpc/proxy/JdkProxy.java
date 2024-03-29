package com.nimo.rpc.proxy;

import com.nimo.rpc.remoting.netty.NettyClient;

import java.lang.reflect.Proxy;

public class JdkProxy {

    public <T> T getProxy(NettyClient client, Class interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaces}, new InvokerInvocationHandler(client));
    }

}
