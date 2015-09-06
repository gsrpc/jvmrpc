package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


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

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteBytes(content);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        content = reader.ReadBytes();

    }
}
