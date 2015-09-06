package com.gsrpc.net;


import java.net.InetSocketAddress;

public interface RemoteResolver {
    InetSocketAddress Resolve() throws Exception;
}
