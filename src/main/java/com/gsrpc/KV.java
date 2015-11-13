package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class KV 
{

    private  byte[] key = new byte[0];

    private  byte[] value = new byte[0];



    public KV(){

    }


    public KV(byte[] key, byte[] value ) {
    
        this.key = key;
    
        this.value = value;
    
    }


    public byte[] getKey()
    {
        return this.key;
    }
    public void setKey(byte[] arg)
    {
        this.key = arg;
    }

    public byte[] getValue()
    {
        return this.value;
    }
    public void setValue(byte[] arg)
    {
        this.value = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeBytes(key);

        writer.writeBytes(value);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            key = reader.readBytes();
        }

        {
            value = reader.readBytes();
        }

    }


}
