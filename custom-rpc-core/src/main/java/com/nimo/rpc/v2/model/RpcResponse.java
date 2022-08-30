package com.nimo.rpc.v2.model;

import java.io.Serializable;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/30
 */
public class RpcResponse implements Serializable {
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
