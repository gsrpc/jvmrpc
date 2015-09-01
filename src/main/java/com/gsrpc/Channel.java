package com.gsrpc;


public interface Channel {
    void send(Request call,Callback callback) throws Exception;
}
