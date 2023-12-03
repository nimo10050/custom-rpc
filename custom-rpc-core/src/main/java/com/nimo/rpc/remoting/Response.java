package com.nimo.rpc.remoting;

import io.netty.util.concurrent.Promise;

import java.util.concurrent.TimeUnit;

public class Response {

    public Response(Promise promise) {
        this.promise = promise;
    }

    private Object result;

    private Promise promise;

    private int timeout = 6000;

    public Object getValue() {
        try {
            promise.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while ((timeout --) > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (result != null)
                return result;
        }
        throw new RuntimeException("获取 value 超时!");
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Promise getPromise() {
        return promise;
    }

    public void setPromise(Promise promise) {
        this.promise = promise;
    }
}
