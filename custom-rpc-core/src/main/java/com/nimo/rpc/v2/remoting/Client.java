package com.nimo.rpc.v2.remoting;

import com.nimo.rpc.v2.model.RpcResponse;

import java.io.*;
import java.net.Socket;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/30
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(os);
            output.writeUTF("com.nimo.rpc.service.HelloService");
            output.writeUTF("sayHello");
            output.flush();
            // output.close();
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(inputStream);
            RpcResponse response = (RpcResponse) input.readObject();
            System.out.println(response.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
