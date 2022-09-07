package com.nimo.rpc.v2;

import com.alibaba.fastjson.JSON;
import com.nimo.rpc.v2.config.GlobalConfig;
import com.nimo.rpc.v2.config.ProtocolConfig;
import com.nimo.rpc.v2.config.RegistryConfig;
import com.nimo.rpc.v2.config.ServiceConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Bootstrap {

    public static void main(String[] args) {
        Bootstrap.start();
    }

    private static String GLOBAL_CONFIG = "{\n" +
            "    \"service\": [{\n" +
            "        \"id\": \"testService\",\n" +
            "        \"interfaceName\": \"com.nimo.rpc.service.HelloService\",\n" +
            "        \"impl\": \"com.nimo.rpc.service.HelloServiceImpl\",\n" +
            "        \"export\": \"protocol1:8888,protocol2:8999\",\n" +
            "        \"registries\": \"registry1\",\n" +
            "        \"group\": \"group1, group2\"\n" +
            "    }],\n" +
            "\n" +
            "    \"protocol\": [{\n" +
            "        \"id\": \"protocol1\",\n" +
            "        \"name\": \"custom\"\n" +
            "    },{\n" +
            "        \"id\": \"protocol2\",\n" +
            "        \"name\": \"custom\"\n" +
            "    }],\n" +
            "\n" +
            "    \"registry\": [{\n" +
            "        \"id\": \"registry1\",\n" +
            "        \"name\": \"registry\",\n" +
            "        \"regProtocol\": \"redis\",\n" +
            "        \"address\": \"127.0.0.1:6379\"\n" +
            "    }]\n" +
            "}";
    
    public static void start() {
        GlobalConfig globalConfig = JSON.parseObject(GLOBAL_CONFIG, GlobalConfig.class);
        List<ServiceConfig> serviceConfigs = globalConfig.getService();
        List<ProtocolConfig> protocolConfigs = globalConfig.getProtocol();
        List<RegistryConfig> registryConfigs = globalConfig.getRegistry();
        for (ServiceConfig serviceConfig : serviceConfigs) {
            String registries = serviceConfig.getRegistries();
            List<RegistryConfig> registryConfigList = findRegistryConfig(registries, registryConfigs);
            serviceConfig.setRegistryConfigs(registryConfigList);

            String export = serviceConfig.getExport();
            List<ProtocolConfig> protocolConfigList =  findProtocolConfig(export, protocolConfigs);
            serviceConfig.setProtocolConfigs(protocolConfigList);

            // 暴露服务
            serviceConfig.export();
        }

    }

    private static List<ProtocolConfig> findProtocolConfig(String export, List<ProtocolConfig> protocolConfigs) {

        List<ProtocolConfig> newProtocolConfigs = new ArrayList<>();
        String[] exports = export.split(",");
        for (String s : exports) {
            String protocolConfigId = s.split(":")[0];
            for (ProtocolConfig protocolConfig : protocolConfigs) {
                if (protocolConfig.getId().equals(protocolConfigId)) {
                    newProtocolConfigs.add(protocolConfig);
                }
            }
        }
        return newProtocolConfigs;
    }

    private static List<RegistryConfig> findRegistryConfig(String registries, List<RegistryConfig> registryConfigs) {
        List<RegistryConfig> newRegistryConfigs = new ArrayList<>();
        for (String registry : registries.split(";")) {
            for (RegistryConfig registryConfig : registryConfigs) {
                if (registryConfig.getId().equals(registry)) {
                    newRegistryConfigs.add(registryConfig);
                }
            }
        }
        return newRegistryConfigs;
    }

}
