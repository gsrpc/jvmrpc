package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * WhoAmI generate by gs2java,don't modify it manually
 */
public class WhoAmI
{

    private  Device iD = new Device();

    private  byte[] context = new byte[0];



    public Device getID()
    {
        return this.iD;
    }
    public void setID(Device arg)
    {
        this.iD = arg;
    }

    public byte[] getContext()
    {
        return this.context;
    }
    public void setContext(byte[] arg)
    {
        this.context = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)2);

        iD.marshal(writer);

        writer.writeBytes(context);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        iD.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

        context = reader.readBytes();
        if(-- __fields == 0) {
            return;
        }

    }
}
