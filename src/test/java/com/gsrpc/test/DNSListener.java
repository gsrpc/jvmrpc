package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



public interface DNSListener {
    String NAME = "com.gsrpc.test.DNSListener";

    void changed (String domain, IPV4 ip) throws Exception;

}

