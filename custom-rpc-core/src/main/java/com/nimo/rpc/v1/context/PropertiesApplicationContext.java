package com.nimo.rpc.v1.context;

import com.nimo.rpc.v1.utils.PropertiesReader;
import com.nimo.rpc.v1.entity.RpcData;
import com.nimo.rpc.v1.proxy.ConsumerProxy;

/**
 * 应用程序上下文 Properties 版本
 */
public class PropertiesApplicationContext {

    private PropertiesReader reader;

    public PropertiesApplicationContext(String properties) {
        reader = new PropertiesReader(properties);
    }

    public Object getBean(String beanName) {
        try {
            RpcData rpcData = reader.fill(beanName);
            Class<?> aClass = rpcData.getCls();
            return ConsumerProxy.build(aClass, rpcData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}
