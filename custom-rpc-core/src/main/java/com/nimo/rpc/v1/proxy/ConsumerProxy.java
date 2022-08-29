package com.nimo.rpc.v1.proxy;

import com.nimo.rpc.v1.entity.RpcData;
import com.nimo.rpc.v1.manager.ServiceManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 服务消费者代理层
 */
public class ConsumerProxy {

    ConsumerProxy() {

    }

    public static Object build(Class proxyCls, RpcData rpcData) {
        return  Proxy.newProxyInstance(proxyCls.getClassLoader(), new Class[]{proxyCls}, new ConsumerProxy().new MyInvocationHandler(rpcData));
    }

    class MyInvocationHandler implements InvocationHandler{

        RpcData rpcData;

        private MyInvocationHandler(RpcData rpcData) {
            this.rpcData = rpcData;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            rpcData.setMethodName(method.getName());
            rpcData.setArgs(args);
            rpcData.setParameterType(method.getParameterTypes());
            return ServiceManager.referService(rpcData);
        }
    }



}
