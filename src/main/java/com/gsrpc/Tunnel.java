package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


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

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)2);

        iD.marshal(writer);

        message.marshal(writer);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        iD.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

        message.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

    }
}
