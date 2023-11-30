package com.nimo.server;

import com.nimo.rpc.v1.manager.ServiceManager;

public class ServiceProvider {

    public static void main(String[] args) throws ClassNotFoundException {
        ServiceManager.exportService("provider.properties");
    }


}
