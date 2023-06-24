package com.nimo.rpc.v3.config;

import com.nimo.rpc.v3.proxy.JdkProxy;
import com.nimo.rpc.v3.remoting.NettyClient;

public class ReferenceConfig<T> {

    private T ref;

    private Class<?> interfaceClass;

    private String interfaceName;

    public void setInterface(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        setInterfaceName(interfaceClass.getName());
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void refer() throws Exception {
        NettyClient client = new NettyClient("localhost", 8080);
        JdkProxy jdkProxy = new JdkProxy();
        ref = jdkProxy.getProxy(client, interfaceClass);
    }

    public T get() {
        return ref;
    }

}
