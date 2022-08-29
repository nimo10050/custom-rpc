package com.nimo.rpc.v2.endpoint;

import com.nimo.rpc.v1.entity.RpcData;
import com.nimo.rpc.v1.utils.IOUtils;
import com.nimo.rpc.v2.URL;
import com.nimo.rpc.v2.config.Provider;
import com.nimo.rpc.v2.model.RpcRequest;
import com.nimo.rpc.v2.serialization.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Server {

    private ServerSocket server;

    private HashMap<String, Provider> providers = new HashMap<>();

    private URL url;

    public void addProvider(Provider provider) {
        providers.put(provider.getUrl().getPath(), provider);
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
                        //Codec codec = new Codec();
                        Provider provider = providers.get(interfaceName);
                        if (provider != null) {
                            Method method = provider.lookupMethod(methodName);
                            try {
                                method.invoke(provider.getProxyImpl());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // Serialization serialization = new Serialization();
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
