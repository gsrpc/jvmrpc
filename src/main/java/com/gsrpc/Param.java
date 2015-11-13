package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class Param 
{

    private  byte[] content = new byte[0];



    public Param(){

    }


    public Param(byte[] content ) {
    
        this.content = content;
    
    }


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

        writer.writeBytes(content);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            content = reader.readBytes();
        }

    }


}
