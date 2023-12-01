package com.nimo.rpc.v3.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

/**
 * @auther zgp
 * @desc dubbo 协议解码器
 * @date 2023/11/30
 */
public class DubboRpcDecoder extends ByteToMessageDecoder {

    private static final short DUBBO_MAGIC_NUMBER = (short) 0xdabb;

    // 3501 - 98条 ,
    // 3502 - 141195 条
    // 3503 - 863 条
    // 3504 - 0条,
    // 3505 - 253589 条
    // 3506 - 5481 条
    // 3507 - 1条,

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 标记
        short magicNum = byteBuf.readShort();
        if (magicNum != DUBBO_MAGIC_NUMBER) {
            // byteBuf.d
            throw new IOException("不符合 dubbo 协议头, 直接丢弃!");
        }
        // 假设是定长的消息
        if (byteBuf.readableBytes() < 8) {
            return;
        }

        list.add(byteBuf.readRetainedSlice(8));
    }
}
