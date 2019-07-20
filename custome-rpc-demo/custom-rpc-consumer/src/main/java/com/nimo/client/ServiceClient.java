package com.nimo.client;

import com.nimo.api.HelloService;
import com.nimo.rpc.context.PropertiesApplicationContext;

public class ServiceClient {

    public static void main(String[] args) {
        // 封装传输对象
        PropertiesApplicationContext context = new PropertiesApplicationContext("consumer.properties");
        HelloService helloService = (HelloService) context.getBean("helloService");
        String result = helloService.sayHello("zhangsan");
        System.out.println(result);
    }




}
