package com.nimo.rpc.v2.layer.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @auther zgp
 * @desc
 * @date 2022/8/29
 */
public class Serialization {

    public <T> T deserialize(byte[] bytes, Class<T> cls) {
        return JSON.parseObject(new String(bytes), cls);
    }


}
