package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * IPV4 generate by gs2java,don't modify it manually
 */
public class IPV4
{

    private  byte[] address = new byte[4];



    public byte[] getAddress()
    {
        return this.address;
    }
    public void setAddress(byte[] arg)
    {
        this.address = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)1);

        writer.writeArrayBytes(address);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        reader.readArrayBytes(address);
        if(-- __fields == 0) {
            return;
        }

    }
}
