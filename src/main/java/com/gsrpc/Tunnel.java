package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * Tunnel generate by gs2java,don't modify it manually
 */
public class Tunnel
{

    private  com.gsrpc.Device iD = new com.gsrpc.Device();

    private  com.gsrpc.Message message = new com.gsrpc.Message();



    public com.gsrpc.Device getID()
    {
        return this.iD;
    }
    public void setID(com.gsrpc.Device arg)
    {
        this.iD = arg;
    }

    public com.gsrpc.Message getMessage()
    {
        return this.message;
    }
    public void setMessage(com.gsrpc.Message arg)
    {
        this.message = arg;
    }

    public void Marshal(Writer writer)  throws Exception
    {

        iD.Marshal(writer);

        message.Marshal(writer);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD.Unmarshal(reader);

        message.Unmarshal(reader);

    }
}
