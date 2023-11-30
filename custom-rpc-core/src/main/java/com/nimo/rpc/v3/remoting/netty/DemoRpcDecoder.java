package com.nimo.rpc.v3.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @auther zgp
 * @desc 协议解码器
 * @date 2023/11/30
 */
public class DemoRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 假设是定长的消息
        if (byteBuf.readableBytes() < 8) {
            return;
        }

        list.add(byteBuf.readRetainedSlice(8));
    }
}
