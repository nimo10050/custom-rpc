package com.nimo.rpc.v2.config;

import com.nimo.rpc.v2.URL;
import org.apache.commons.lang3.StringUtils;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class RegistryConfig {

    private String id;

    private String name;

    private String regProtocol;

    private String address;

    public URL toURL() {
        URL url = new URL();
        if ("redis".equals(regProtocol)) {
            url.setName(regProtocol);
            String[] ap = address.split(":");
            url.setHost(ap[0]);
            url.setPort(ap[1]);
        }
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegProtocol() {
        return regProtocol;
    }

    public void setRegProtocol(String regProtocol) {
        this.regProtocol = regProtocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
