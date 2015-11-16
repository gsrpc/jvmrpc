package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


public class WhoAmI 
{

    private  Device iD = new Device();

    private  byte[] context = new byte[0];



    public WhoAmI(){

    }


    public WhoAmI(Device iD, byte[] context ) {
    
        this.iD = iD;
    
        this.context = context;
    
    }


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

        iD.marshal(writer);

        writer.writeBytes(context);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            iD.unmarshal(reader);
        }

        {
            context = reader.readBytes();
        }

    }


}
