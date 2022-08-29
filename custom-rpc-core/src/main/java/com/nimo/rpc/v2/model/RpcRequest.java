package com.nimo.rpc.v2.model;

import com.nimo.rpc.v2.serialization.Serialization;

import java.io.Serializable;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
