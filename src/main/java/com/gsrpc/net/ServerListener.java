package com.gsrpc.net;

import com.gsrpc.DispatcherChannel;

/**
 * The gsrpc server listener interface .
 */
public interface ServerListener {
    void addClient(DispatcherChannel dispatcherChannel) throws Exception;
    void removeClient(DispatcherChannel dispatcherChannel) throws Exception;
}
