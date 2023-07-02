package com.nimo.rpc.v3.remoting.api;

import com.nimo.rpc.v3.remoting.Response;

import java.net.InetAddress;
import java.net.SocketAddress;

public interface Client {

    void send(Object request);

    Response send(Object request, int timeout);

    void connect(SocketAddress address);

}
