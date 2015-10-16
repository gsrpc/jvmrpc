package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


public class UnknownException extends Exception
{


    public UnknownException() {
    
    }


    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)0);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

    }
}
