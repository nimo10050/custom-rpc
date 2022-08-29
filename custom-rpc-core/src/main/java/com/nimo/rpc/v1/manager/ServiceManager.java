package com.nimo.rpc.v1.manager;

import com.nimo.rpc.v1.exception.RpcException;
import com.nimo.rpc.v1.utils.IOUtils;
import com.nimo.rpc.v1.utils.PropertiesReader;
import com.nimo.rpc.v1.entity.RpcData;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * 服务管理
 * 1. 引用服务
 * 2. 暴露服务
 */
public class ServiceManager {

    /**
     * 引用服务
     *
     * @param rpcData
     * @return
     */
    public static Object referService(RpcData rpcData) {

        Socket client = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;

        try {
            // 1. 建立连接
            client = new Socket(rpcData.getHost(), rpcData.getPort());
            os = client.getOutputStream();
            oos = new ObjectOutputStream(os);
            // 2. 序列化写入
            oos.writeObject(rpcData);
            // 3. 读取返回结果
            try {
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                Object result = input.readObject();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            return e;
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(client);
        }
        return null;
    }


    /**
     * 暴露服务
     *
     * @param rpcData
     * @return
     */
    public static void exportService(String properties) {
        try {
            // 1. 读取本地配置文件
            PropertiesReader propReader = new PropertiesReader(properties);
            String port = propReader.getProperty("export.port");
            Map<String, RpcData> services = propReader.fillAll();

            // 2. 开通服务端连接
            ServerSocket server = new ServerSocket(Integer.valueOf(port));
            while (true) {
                Socket socket = server.accept();
                
                new Thread(() -> {
                    InputStream inputStream = null;
                    ObjectInputStream ois = null;
                    ObjectOutputStream oos = null;
                    try {
                        // 3. 获取客户端输入流
                        inputStream = socket.getInputStream();
                        ois = new ObjectInputStream(inputStream);
                        RpcData remote = (RpcData) ois.readObject();
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        // 4.1 方法不存在
                        if (services == null || !services.containsKey(remote.getServiceName())) {
                            oos.writeObject("service is not exist!");
                        } else {
                            // 4.2 调用本地方法
                            remote.setServiceImplQualifyName(propReader.getProperty(remote.getServiceName() + ".impl"));
                            Object result = invokeNativeMethod(remote);
                            // 5. 返回结果给服务调用方
                            oos.writeObject(result);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        IOUtils.closeQuietly(oos);
                        IOUtils.closeQuietly(ois);
                        IOUtils.closeQuietly(inputStream);
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 服务提供者调用本地方法
     *
     * @param rpcData
     * @return
     * @throws Exception
     */
    private static Object invokeNativeMethod(RpcData rpcData) throws RpcException {
        try {
            Object service = Class.forName(rpcData.getServiceImplQualifyName()).newInstance();
            Method method = rpcData.getCls().getMethod(rpcData.getMethodName(), rpcData.getParameterType());
            Object result = method.invoke(service, rpcData.getArgs());
            return result;
        } catch (InstantiationException e) {
            return e;
        } catch (InvocationTargetException e) {
            return e;
        } catch (NoSuchMethodException e) {
            return e;
        } catch (IllegalAccessException e) {
            return e;
        } catch (ClassNotFoundException e) {
            return e;
        }
    }

}
