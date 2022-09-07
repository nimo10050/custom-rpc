package com.nimo.rpc.v2.endpoint;

import com.nimo.rpc.v1.utils.IOUtils;
import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.config.Provider;
import com.nimo.rpc.v2.model.RpcRequest;
import com.nimo.rpc.v2.model.RpcResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Server {

    private ServerSocket server;

    private URL url;

    private Map<String, Provider> providers = new HashMap<>();

    public void addProvider(String key, Provider provider) {
        providers.put(key, provider);
    }

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
                        ObjectInputStream input = new ObjectInputStream(inputStream);
                        String interfaceName = input.readUTF();
                        String methodName = input.readUTF();

                        // 组装 request 对象
                        RpcRequest request = new RpcRequest();
                        request.setInterfaceName(interfaceName);
                        request.setMethodName(methodName);

                        Provider provider = providers.get(interfaceName);
                        if (provider != null) {
                            RpcResponse response = provider.invoke(request);
                            OutputStream outputStream = socket.getOutputStream();
                            ObjectOutputStream out = new ObjectOutputStream(outputStream);
                            out.writeObject(response);
                            out.flush();
                            out.close();
                        }

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
