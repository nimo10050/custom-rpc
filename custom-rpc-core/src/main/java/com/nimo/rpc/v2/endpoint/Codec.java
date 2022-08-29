package com.nimo.rpc.v2.endpoint;

import com.nimo.rpc.v2.model.RpcRequest;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Codec {

    public RpcRequest decode(byte[] buf) {
        return new RpcRequest();
    }

}
