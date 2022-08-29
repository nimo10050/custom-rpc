package com.nimo.rpc.v2.endpoint;

import com.nimo.rpc.v1.entity.RpcData;
import com.nimo.rpc.v1.utils.IOUtils;
import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.model.RpcRequest;
import com.nimo.rpc.v2.serialization.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Server {

    private ServerSocket server;

    private URL url;

    public Server(URL url) {
        try {
            this.url = url;
            server = new ServerSocket(Integer.valueOf(url.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            while (true) {
                Socket socket = server.accept();
                new Thread(() -> {
                    InputStream inputStream = null;
                    try {
                        // 3. 获取客户端输入流
                        inputStream = socket.getInputStream();
                        byte[] buf = new byte[1024];
                        inputStream.read(buf);
                        System.out.println("receive consumer message : " + new String(buf));
                        //Codec codec = new Codec();
                        //RpcRequest request = codec.decode(buf);
                        //Serialization serialization = new Serialization();
                        // serialization.deserialize(buf, url.getClass()).var
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
