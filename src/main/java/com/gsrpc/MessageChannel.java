package com.gsrpc;


public interface MessageChannel extends Channel {
    void send(Message message) throws Exception;
}
