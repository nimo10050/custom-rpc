package com.nimo.rpc.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.Cleanable;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.fastjson.FastJsonSerialization;
import org.apache.dubbo.remoting.exchange.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class DubboRpcEncoder extends MessageToByteEncoder {

    private static final short DUBBO_MAGIC_NUMBER = (short) 0xdabb;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buffer) throws Exception {
        int savedWriteIndex = buffer.writerIndex();
        // header.
        byte[] header = new byte[16];
        // set magic number.
        Bytes.short2bytes(DUBBO_MAGIC_NUMBER, header);
        // set request and serialization flag.
        // 0000 0110
        header[2] = 6;
        // set response status.
        byte status = 20;
        header[3] = status;
        // set request id.
        Bytes.long2bytes(1, header, 4);

        //buffer.writerIndex(savedWriteIndex + 16);
        FastJsonSerialization serialization = new FastJsonSerialization();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //baos.write(((String) msg).getBytes(StandardCharsets.UTF_8));

        ObjectOutput out = serialization.serialize(null, baos);

        Response res = (Response) msg;
        out.writeByte((byte) 1);
        out.writeObject(res);
        int len = baos.size();
        Bytes.int2bytes(len, header, 12);
        // write header.
        buffer.writeBytes(header);
        // write body
        buffer.writeBytes(baos.toByteArray());

        //out.w
        if (out instanceof Cleanable) {
            ((Cleanable) out).cleanup();
        }
    }
}
