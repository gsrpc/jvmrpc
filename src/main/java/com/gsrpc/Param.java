package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * Param generate by gs2java,don't modify it manually
 */
public class Param
{

    private  byte[] content = new byte[0];



    public byte[] getContent()
    {
        return this.content;
    }
    public void setContent(byte[] arg)
    {
        this.content = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)1);

        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        content = reader.readBytes();
        if(-- __fields == 0) {
            return;
        }

    }
}
