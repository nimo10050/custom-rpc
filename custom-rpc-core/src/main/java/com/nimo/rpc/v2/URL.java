package com.nimo.rpc.v2;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class URL {

    private String path;

    public URL() {
    }

    public URL(String name, String host, String port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    private String name;

    private String host;

    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
