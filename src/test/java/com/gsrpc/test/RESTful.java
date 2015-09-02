package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;



public interface RESTful {

    void Post (String name, byte[] content) throws Exception;

    byte[] Get (String name) throws Exception;

}

