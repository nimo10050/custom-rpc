package com.nimo.server;

import com.nimo.api.HelloService;

public class HelloService2Impl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "bye bye " + name;
    }
}
