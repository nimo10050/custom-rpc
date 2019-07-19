package com.nimo.rpc.manager;

import com.nimo.rpc.Utils.IOUtils;
import com.nimo.rpc.entity.RpcData;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

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
    public static void exportService(Integer port) {
        ServerSocket server = null;
        InputStream inputStream = null;
        ObjectInputStream ois = null;

        try {
            // 1. 开通服务端连接
            server = new ServerSocket(port);
            Socket socket = server.accept();
            // 2. 获取客户端输入流
            inputStream = socket.getInputStream();
            ois = new ObjectInputStream(inputStream);
            RpcData rpcData = (RpcData) ois.readObject();
            // 3. 调用本地方法
            Object result = invokeNativeMethod(rpcData);
            // 4. 返回结果给服务调用方
            new ObjectOutputStream(socket.getOutputStream()).writeObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(server);
        }
    }

    /**
     * 服务提供者调用本地方法
     *
     * @param rpcData
     * @return
     * @throws Exception
     */
    private static Object invokeNativeMethod(RpcData rpcData) throws Exception {
        Object service = Class.forName(rpcData.getImplClassQualifyName()).newInstance();
        //Object service = rpcData.getCls().newInstance();
        Method method = rpcData.getCls().getMethod(rpcData.getMethodName(), rpcData.getParameterType());
        Object result = method.invoke(service, rpcData.getArgs());
        return result;

    }
}
