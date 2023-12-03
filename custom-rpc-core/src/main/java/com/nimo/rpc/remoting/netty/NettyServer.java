package com.nimo.rpc.remoting.netty;

import com.nimo.rpc.v3.remoting.netty.DubboRpcDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public void start(String ip, Integer port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("demp-rpc-decoder", new DubboRpcDecoder());
                        //ch.pipeline().addLast("demo-rpc-encoder", new DemoRpcEncoder());
                        ch.pipeline().addLast("server-handler", new NettyServerHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(port);

        Channel channel = channelFuture.channel();

        channel.closeFuture();

    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.start("localhost", 8989);
    }

}
