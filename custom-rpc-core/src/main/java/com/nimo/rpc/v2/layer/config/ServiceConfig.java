package com.nimo.rpc.v2.layer.config;

import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.export.Exporter;
import com.nimo.rpc.v2.layer.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class ServiceConfig<T> {

    private String id;

    // 指向接口对应的实现类
    private T ref;

    private String impl;

    // 对外暴露的接口
    private String interfaceName;

    private Class interfaceClass;

    // 格式：协议名:端口,协议名:端口
    private String export;

    private String host;

    private String registries;

    private List<URL> registryUrls;

    // 协议配置
    private List<ProtocolConfig> protocolConfigs;

    // 注册中心配置
    private List<RegistryConfig> registryConfigs;

    public List<ProtocolConfig> getProtocolConfigs() {
        return protocolConfigs;
    }

    public void setProtocolConfigs(List<ProtocolConfig> protocolConfigs) {
        this.protocolConfigs = protocolConfigs;
    }

    // 同一个 service 可能同时以不同的协议、不同的分组暴露出来
    private List<Exporter> exporters = new ArrayList<>();

    public void export() {
        try {
            interfaceClass = Class.forName(interfaceName);
            Class implClass = Class.forName(impl);
            ref = (T) implClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        loadRegistryUrls();
        Map<String, String> portMap = getProtocolAndPort(export);
        for (ProtocolConfig protocolConfig : protocolConfigs) {
            doExport(protocolConfig, portMap.get(protocolConfig.getId()));
        }
        System.out.println("exported.");
    }

    private void loadRegistryUrls() {
        if (registryUrls == null) {
            registryUrls = new ArrayList<>();
        }
        for (RegistryConfig registryConfig : registryConfigs) {
            registryUrls.add(registryConfig.toURL());
        }
    }

    private void doExport(ProtocolConfig protocolConfig, String port) {
        String hostName = host;
        if (StringUtils.isEmpty(host)) {
            hostName = getLocalIpAddress();
        }
        
        URL serviceUrl = new URL(protocolConfig.getId(), hostName, port, interfaceClass.getName());
        exportService(serviceUrl);
    }

    private void exportService(URL serviceUrl) {
        Protocol protocol = new Protocol();

        exporters.add(protocol.export(new Provider<T>(ref, interfaceClass, serviceUrl), serviceUrl));
        registry(registryUrls);
    }

    private void registry(List<URL> registryUrls) {
        // redis
    }

    private String getLocalIpAddress() {
        return "localhost";
    }

    private Map<String, String> getProtocolAndPort(String export) {
        Map<String, String> portMap = new HashMap<>();
        String[] exports = export.split(",");
        for (String s : exports) {
            String[] pp = s.split(":");
            portMap.put(pp[0], pp[1]);
        }
        return portMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public List<RegistryConfig> getRegistryConfigs() {
        return registryConfigs;
    }

    public void setRegistryConfigs(List<RegistryConfig> registryConfigs) {
        this.registryConfigs = registryConfigs;
    }

    public String getRegistries() {
        return registries;
    }

    public void setRegistries(String registries) {
        this.registries = registries;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
