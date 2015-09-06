package com.gsrpc;

import io.netty.util.Timeout;

public abstract class Callback {

    private Timeout timer;

    /**
     * get callback timeout value
     * @return timeout duration which unit is second
     */
    public abstract int getTimeout();

    /**
     * rpc call return handler
     * @param e catch exception of rpc call
     * @param callReturn rpc result value
     */
    public abstract void Return(Exception e,com.gsrpc.Response callReturn);


    public Timeout getTimer() {
        return timer;
    }

    public void setTimer(Timeout timer) {
        this.timer = timer;
    }
}