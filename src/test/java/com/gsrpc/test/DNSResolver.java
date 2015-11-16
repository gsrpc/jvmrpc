package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



public interface DNSResolver {
    public static final String NAME = "com.gsrpc.test.DNSResolver";

    IPV4 resolve (String domain) throws Exception;

    void asyncResolve (String domain) throws Exception;

}

