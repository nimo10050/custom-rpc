package com.nimo.rpc.v3.remoting.netty;

import com.nimo.rpc.v3.remoting.Response;
import com.nimo.rpc.v3.remoting.ResponseFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler {

    private NettyClient client;

    public NettyClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client connected.");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Hello ,服务器", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read.....");
        this.client.getCallbackMap().get("123").setResult("hello world");
        System.out.println("read over");
    }

}
