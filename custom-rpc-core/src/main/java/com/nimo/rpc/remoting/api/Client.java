package com.nimo.rpc.remoting.api;

import com.nimo.rpc.remoting.Response;

import java.net.SocketAddress;

public interface Client {

    void send(Object request);

    Response send(Object request, int timeout);

    void connect(SocketAddress address);

}
