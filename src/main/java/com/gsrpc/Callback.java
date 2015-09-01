package com.gsrpc;

public interface Callback {
    /**
     * get callback timeout value
     * @return timeout duration which unit is second
     */
    int getTimeout();

    /**
     * rpc call return handler
     * @param e
     * @param callReturn
     */
    void Return(Exception e,com.gsrpc.Response callReturn);
}