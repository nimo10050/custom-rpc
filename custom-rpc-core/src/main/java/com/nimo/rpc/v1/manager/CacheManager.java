package com.nimo.rpc.v1.manager;

import com.nimo.rpc.v1.entity.RpcData;
import com.nimo.rpc.v1.utils.IOUtils;

/**
 * 服务缓存处理
 */
public class CacheManager {

    /**
     * 把服务拉到本地进行缓存
     *
     * @param rpcData
     */
    public static void pullService2Local(RpcData rpcData) {
        // 将 rpcData 对象序列化到本地
        if (rpcData != null) {
            IOUtils.writeObject(rpcData.getServiceName() +"-"+ rpcData.getMethodName(), rpcData);
        }
    }


    /**
     * 读取本地缓存
     * @param methodName
     * @param serviceName
     * @return
     */
    public static RpcData loadServiceFromLocal(String methodName,String serviceName) {
        RpcData rpcData = (RpcData) IOUtils.readObject(methodName + "-" + serviceName);
        return rpcData;
    }

}
