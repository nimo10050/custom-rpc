package com.nimo.proxy;

import com.nimo.rpc.entity.RpcData;
import com.nimo.rpc.manager.ServiceManager;

public class ServiceClient {

    public static void main(String[] args) {
        // 封装传输对象
        RpcData rpcData = new RpcData();
        rpcData.setHost("localhost");
        rpcData.setPort(8866);
        rpcData.setImplClassQualifyName("com.nimo.proxy.HelloServiceImpl");
        rpcData.setCls(HelloService.class);
        rpcData.setArgs(new String[]{"zhangsan"});
        rpcData.setParameterType(new Class[]{String.class});
        rpcData.setMethodName("sayHello");

        System.out.println(ServiceManager.referService(rpcData));
    }




}
