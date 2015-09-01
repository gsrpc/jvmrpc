package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



public interface RESTful {

    void Post (String name, byte[] content) throws Exception;

    byte[] Get (String name) throws Exception;

}

