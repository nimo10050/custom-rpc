package com.nimo.rpc.v2.export;

import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.config.Provider;
import com.nimo.rpc.v2.endpoint.Server;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Exporter {

    private Server server;

    private Provider provider;

    public <T> Exporter(Provider<T> provider, URL serviceUrl) {
        server = new Server(serviceUrl);
        server.addProvider(provider);
    }

    public void init() {
        new Thread(() -> {
            server.open();
        }).start();
    }

}