package com.nimo.rpc.remoting.netty;

import com.nimo.rpc.remoting.Response;
import com.nimo.rpc.remoting.api.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultPromise;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class NettyClient implements Client {
    protected Bootstrap clientBootstrap;

    protected EventLoopGroup group;

    private ChannelFuture channel;

    private Map<String, Response> callbackMap = new HashMap<>();

    public Map<String, Response> getCallbackMap() {
        return callbackMap;
    }

    public NettyClient() throws Exception {

        clientBootstrap = new Bootstrap();
        // 创建并配置客户端Bootstrap

        group = new NioEventLoopGroup(1);

        clientBootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                // 指定ChannelHandler的顺序
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("client-handler", new NettyClientHandler(NettyClient.this));
                    }
                });
    }


    @Override
    public void send(Object msg) {
        this.channel.channel().writeAndFlush(msg);
    }

    @Override
    public Response send(Object request, int timeout) {
        Response response = new Response(new DefaultPromise(new DefaultEventLoop()));
        callbackMap.put("123", response);
        ChannelFuture channelFuture = this.channel.channel().writeAndFlush(request);
        return response;
    }

    @Override
    public void connect(SocketAddress address) {
        channel = clientBootstrap.connect(address);
        channel.awaitUninterruptibly();
    }

    public void close() {
        group.shutdownGracefully();

    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.connect(new InetSocketAddress("localhost", 8989));
        Response response = client.send(Unpooled.copiedBuffer("123456781234567812345678", CharsetUtil.UTF_8), 6000);
        System.out.println("receive server message: " + response.getValue());
    }

}
