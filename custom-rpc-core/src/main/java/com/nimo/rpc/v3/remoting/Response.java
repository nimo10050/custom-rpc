package com.nimo.rpc.v3.remoting;

public class Response {

    private Object result;

    private int timeout = 6000;

    public Object getValue() {
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
}
