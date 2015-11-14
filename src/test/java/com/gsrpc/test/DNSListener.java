package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



public interface DNSListener {

    void changed (String domain, IPV4 ip) throws Exception;

}

