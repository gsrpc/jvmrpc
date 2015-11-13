package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class IPV4 
{

    private  byte[] address = new byte[0];



    public IPV4(){

    }


    public IPV4(byte[] address ) {
    
        this.address = address;
    
    }


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

        writer.writeBytes(address);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            address = reader.readBytes();
        }

    }


}
