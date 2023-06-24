package com.nimo.rpc.v3.remoting;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    protected Bootstrap clientBootstrap;

    protected EventLoopGroup group;

    private String host;

    private int port;

    private ChannelFuture channel;

    public NettyClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
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
                        ch.pipeline().addLast("client-handler", new NettyClientHandler());
                    }
                });
    }

    public void connect() { // 连接指定的地址和端口
        channel = clientBootstrap.connect(host, port);
        channel.awaitUninterruptibly();
    }

    public void send(Object msg) {
        this.channel.channel().writeAndFlush(msg);
    }

    public void close() {

        group.shutdownGracefully();

    }

}
