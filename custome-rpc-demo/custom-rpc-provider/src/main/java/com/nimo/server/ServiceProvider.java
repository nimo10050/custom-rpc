package com.nimo.server;

import com.nimo.rpc.manager.ServiceManager;

public class ServiceProvider {

    public static void main(String[] args) {
        ServiceManager.exportService("provider.properties");
    }


}
