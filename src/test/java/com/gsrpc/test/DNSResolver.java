package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;



public interface DNSResolver {

    IPV4 resolve (String domain) throws Exception;

}

