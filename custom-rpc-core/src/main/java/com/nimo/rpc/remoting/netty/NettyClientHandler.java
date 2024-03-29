package com.nimo.rpc.remoting.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler {

    private NettyClient client;

    public NettyClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("12345678");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Hello ,服务器", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read.....");
        this.client.getCallbackMap().get("123").setResult("123456781234567812345678");
        System.out.println("read over");
    }

}
