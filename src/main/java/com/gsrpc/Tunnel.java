package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Tunnel generate by gs2java,don't modify it manually
 */
public class Tunnel
{

    private  Device iD = new Device();

    private  Message message = new Message();



    public Device getID()
    {
        return this.iD;
    }
    public void setID(Device arg)
    {
        this.iD = arg;
    }

    public Message getMessage()
    {
        return this.message;
    }
    public void setMessage(Message arg)
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
