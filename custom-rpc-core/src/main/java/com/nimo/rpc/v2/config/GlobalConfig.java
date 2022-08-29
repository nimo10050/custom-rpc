package com.nimo.rpc.v2.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class GlobalConfig {
    private List<ServiceConfig> service = new ArrayList<>();

    private List<RegistryConfig> registry = new ArrayList<>();

    private List<ProtocolConfig> protocol = new ArrayList<>();

    public List<ServiceConfig> getService() {
        return service;
    }

    public void setService(List<ServiceConfig> service) {
        this.service = service;
    }

    public List<RegistryConfig> getRegistry() {
        return registry;
    }

    public void setRegistry(List<RegistryConfig> registry) {
        this.registry = registry;
    }

    public List<ProtocolConfig> getProtocol() {
        return protocol;
    }

    public void setProtocol(List<ProtocolConfig> protocol) {
        this.protocol = protocol;
    }
}

