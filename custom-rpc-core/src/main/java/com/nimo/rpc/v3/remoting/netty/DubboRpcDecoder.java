package com.nimo.rpc.v3.remoting.netty;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.dubbo.common.io.Bytes;
import org.apache.dubbo.common.serialize.Cleanable;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.fastjson.FastJsonSerialization;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.ServiceRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.dubbo.common.URL.buildKey;
import static org.apache.dubbo.common.constants.CommonConstants.*;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;

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
        byte serializationId = (byte) (0x1f & flag);
        System.out.println("serializationId: " + serializationId);
//
//        //Status (8 bits) 仅在 Req/Res 为0（响应）时有用，用于标识响应的状态
        byteBuf.skipBytes(1);// skip status

//        //Request ID (64 bits)标识唯一请求。类型为long。
        long requestId = byteBuf.readLong();
        System.out.println("requestId: " + requestId);
//
//        //Data Length (32 bits)
        int len = byteBuf.readInt();
        System.out.println("dataLength: " + len);

        byte[] buf = new byte[len];
        byteBuf.readBytes(buf);
        FastJsonSerialization serialization = new FastJsonSerialization();
        ObjectInput in = serialization.deserialize(null, new ByteArrayInputStream(buf));

        String dubboVersion = in.readUTF();
        System.out.println("dubbo version: " + dubboVersion);

        String path = in.readUTF();
        System.out.println("dubbo path: " + path);

        String version = in.readUTF();
        System.out.println("version: " + version);

        String methodName = in.readUTF();
        System.out.println("methodName: " + methodName);

        String desc = in.readUTF();
        System.out.println("desc: " + desc);

        try {
            Object[] args = new Object[0];
            Class<?>[] pts = new Class[0];
            if (desc.length() > 0) {
                pts = ReflectUtils.desc2classArray(desc);
                args = new Object[pts.length];
                for (int i = 0; i < args.length; i++) {
                    try {
                        args[i] = in.readObject(pts[i]);
                        System.out.println("args[" + i + "] : " + args[i]);
                    } catch (Exception e) {
                        System.out.println("Decode argument failed: " + e.getMessage());
                    }
                }
            }
            // invoke method
            Object instance = Class.forName(path).newInstance();
            Method method = instance.getClass().getMethod(methodName, pts);
            Object result = method.invoke(instance, args);
            System.out.println("result: " + result);
            Response res = new Response();
            res.setResult(result);
            list.add(res);
        } catch (ClassNotFoundException e) {
            throw new IOException(StringUtils.toString("Read invocation data failed.", e));
        } finally {
            if (in instanceof Cleanable) {
                ((Cleanable) in).cleanup();
            }
        }

    }
}
