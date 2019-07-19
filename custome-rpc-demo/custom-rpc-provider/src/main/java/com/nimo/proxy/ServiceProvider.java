package com.nimo.proxy;

import com.nimo.rpc.manager.ServiceManager;

public class ServiceProvider {

    public static void main(String[] args) {
        ServiceManager.exportService(8866);
    }


}
