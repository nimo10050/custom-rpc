/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nimo.rpc.v3.proxy;


import com.nimo.rpc.v3.remoting.NettyClient;
import com.nimo.rpc.v3.remoting.Request;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvokerInvocationHandler implements InvocationHandler {
    private NettyClient client;

    public InvokerInvocationHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request rpcRequest = new Request();
        rpcRequest.setServiceName(proxy.getClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(method.getParameters());
        rpcRequest.setParameterTypes(method.getParameterTypes());
        client.send(rpcRequest);
        return null;
    }
}
