package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class InvalidContractException extends Exception
{


    public InvalidContractException() {
    
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
