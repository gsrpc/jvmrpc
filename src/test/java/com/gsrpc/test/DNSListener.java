package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;



public interface DNSListener {
    String NAME = "com.gsrpc.test.DNSListener";

    void changed (String domain, IPV4 ip) throws Exception;

}

