package com.gsrpc;

import com.gsrpc.Device;

import com.gsrpc.Message;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class Tunnel 
{

    private  Device iD = new Device();

    private  Message message = new Message();



    public Tunnel(){

    }


    public Tunnel(Device iD, Message message ) {
    
        this.iD = iD;
    
        this.message = message;
    
    }


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

        iD.marshal(writer);

        message.marshal(writer);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            iD.unmarshal(reader);
        }

        {
            message.unmarshal(reader);
        }

    }


}
