package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


public class RemoteException extends Exception
{


    public RemoteException(String message) {
        super(message);
    }

    public void Marshal(Writer writer)  throws Exception
    {

    }
    public void Unmarshal(Reader reader) throws Exception
    {

    }
}
