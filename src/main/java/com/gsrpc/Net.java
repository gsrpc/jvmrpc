package com.gsrpc;


public interface Net {
    void send(Request call,Callback callback) throws Exception;
}
