package com.nimo.rpc.v3.remoting.netty;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.dubbo.common.io.Bytes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @auther zgp
 * @desc dubbo 协议解码器
 * @date 2023/11/30
 */
public class DubboRpcDecoder extends ByteToMessageDecoder {

    private static final short DUBBO_MAGIC_NUMBER = (short) 0xdabb;
    private static final byte DUBBO_MAGIC_NUMBER_HIGH = (byte) 0xda;
    private static final byte DUBBO_MAGIC_NUMBER_LOW = (byte) 0xbb;

    // 0 0 1 44
    // 0000 0001 0010 1100//256 32 8 4

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //byte[] header = new byte[16];
        //byteBuf.readBytes(header);
        // 标记当前位置
        byteBuf.markReaderIndex();
        //int len = Bytes.bytes2int(header, 12);
        //byte magicHigh = header[0];
        //byte magicLow = header[1];
        //if (magicHigh != DUBBO_MAGIC_NUMBER_HIGH || magicLow != DUBBO_MAGIC_NUMBER_LOW) {
        if (byteBuf.readShort() != DUBBO_MAGIC_NUMBER) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            throw new IOException("不符合 dubbo 协议头, 直接丢弃!");
        }

        byteBuf.resetReaderIndex();
//        // Magic - Magic High & Magic Low (16 bits)标识协议版本号，Dubbo 协议：0xdabb
        byteBuf.skipBytes(2); //skip magic number
//
//        //Req/Res (1 bit)标识是请求或响应。请求： 1; 响应： 0。
        byte flag = byteBuf.readByte();
//
//        //2 Way (1 bit) 仅在 Req/Res 为1（请求）时才有用，标记是否期望从服务器返回值。如果需要来自服务器的返回值，则设置为1。
//
//        //Event (1 bit) 标识是否是事件消息，例如，心跳事件。如果这是一个事件，则设置为1。
//        byte event = byteBuf.readByte();
//        System.out.println("event: " + event);
//
//        //Serialization ID (5 bit)标识序列化类型：比如 fastjson 的值为6。
//        byte serializationId = (byte) (0x1f & flag);
//        System.out.println("serializationId: " + serializationId);
//
//        //Status (8 bits) 仅在 Req/Res 为0（响应）时有用，用于标识响应的状态
        byteBuf.skipBytes(1);// skip status
//        //。
//        //
//        //20 - OK
//        //30 - CLIENT_TIMEOUT
//        //31 - SERVER_TIMEOUT
//        //40 - BAD_REQUEST
//        //50 - BAD_RESPONSE
//        //60 - SERVICE_NOT_FOUND
//        //70 - SERVICE_ERROR
//        //80 - SERVER_ERROR
//        //90 - CLIENT_ERROR
//        //100 - SERVER_THREADPOOL_EXHAUSTED_ERROR
//
//        //Request ID (64 bits)标识唯一请求。类型为long。
        long requestId = byteBuf.readLong();
        System.out.println("requestId: " + requestId);
//        //
//        //Data Length (32 bits)
        int len = byteBuf.readInt();
        System.out.println("dataLength: " + len);

        byte[] buf = new byte[len];
        byteBuf.readBytes(buf);

        Hessian2Input input = new Hessian2Input(null);
        input.init(new ByteArrayInputStream(buf));
        //input.readObject()
        System.out.println(input);
        list.add(byteBuf.readRetainedSlice(8));
    }
}
