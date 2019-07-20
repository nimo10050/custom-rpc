package com.nimo.rpc.utils;

import com.nimo.rpc.entity.RpcData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具类
 */
public class PropertiesReader {

    private Properties prop;

    public PropertiesReader(String prop){
        loadProperties(prop);
    }

    private void loadProperties(String properties){
        prop = new Properties();
        InputStream is = this.getClass()
                .getClassLoader().getResourceAsStream(properties);
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据服务名读取服务信息
     * @param key 服务名
     * @return
     */
    public RpcData fill(String key) {

        RpcData rpcData = new RpcData();
        String interfaceName = prop.getProperty(key + ".interface");
        String host = prop.getProperty(key + ".host");
        String port = prop.getProperty(key + ".port");
        String impl = prop.getProperty(key +".impl");
        rpcData.setServiceName(key);
        rpcData.setHost(host);
        rpcData.setPort(StringUtils.isNotEmpty(port)? Integer.valueOf(port): 0);// 空指针判断
        rpcData.setServiceImplQualifyName(impl);
        try {
            Class<?> aClass = Class.forName(interfaceName);
            rpcData.setCls(aClass);
            return rpcData;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getProperty(String key) {
        String serviceName = prop.getProperty(key);
        return serviceName;
    }
}
