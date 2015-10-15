package com.gsrpc;



public interface Sink extends MessageChannel {

    void registerDispatcher(short id,Dispatcher dispatcher);

    void unregisterDispatcher(short id,Dispatcher dispatcher);
}
