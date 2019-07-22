package com.nimo.rpc.utils;

import com.nimo.rpc.entity.RpcData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取工具类
 */
public class PropertiesReader {

    private Properties prop;

    public PropertiesReader(String prop) {
        loadProperties(prop);
    }

    /**
     * 加载配置文件
     * @param properties
     */
    private void loadProperties(String properties) {
        prop = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(properties);
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据服务名读取服务信息
     *
     * @param key 服务名
     * @return
     */
    public RpcData fill(String serviceName) throws ClassNotFoundException {

        RpcData rpcData = new RpcData();
        String interfaceName = prop.getProperty(serviceName + ".interface");
        if (StringUtils.isEmpty(interfaceName)) {
            throw new IllegalStateException("interface name not allow null");
        }
        // TODO 校验
        String host = prop.getProperty(serviceName + ".host");
        String port = prop.getProperty(serviceName + ".port");
        String impl = prop.getProperty(serviceName + ".impl");
        rpcData.setServiceName(serviceName);
        rpcData.setHost(host);
        rpcData.setPort(StringUtils.isNotEmpty(port) ? Integer.valueOf(port) : 0);// 空指针判断
        rpcData.setServiceImplQualifyName(impl);
        try {
            Class<?> aClass = Class.forName(interfaceName);
            rpcData.setCls(aClass);
            return rpcData;
        } catch (ClassNotFoundException e) {
            throw e;
        }

    }

    /**
     * 根加载所有配置的服务
     *
     * @return
     */
    public Map<String, RpcData> fillAll() throws ClassNotFoundException {
        String exportServicesProp = prop.getProperty("export.services");
        if (StringUtils.isEmpty(exportServicesProp)) {
            return null;
        }

        Map<String, RpcData> services = new HashMap<>();
        String[] exportServicePropArray = exportServicesProp.split(",");
        for (int i = 0; i < exportServicePropArray.length; i++) {
            String exportServiceProp = exportServicePropArray[i];
            RpcData rpcData = fill(exportServiceProp);
            services.put(exportServiceProp, rpcData);
        }
        return services;
    }

    /**
     * 获取配置值
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String serviceName = prop.getProperty(key);
        return serviceName;
    }
}
