package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


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

    public void Marshal(Writer writer)  throws Exception
    {

        iD.Marshal(writer);

        writer.WriteBytes(context);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD.Unmarshal(reader);

        context = reader.ReadBytes();

    }
}
