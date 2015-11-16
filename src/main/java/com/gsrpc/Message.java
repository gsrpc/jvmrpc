package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class Message 
{

    private  Code code = Code.Heartbeat;

    private  byte agent = 0;

    private  byte[] content = new byte[0];



    public Message(){

    }


    public Message(Code code, byte agent, byte[] content ) {
    
        this.code = code;
    
        this.agent = agent;
    
        this.content = content;
    
    }


    public Code getCode()
    {
        return this.code;
    }
    public void setCode(Code arg)
    {
        this.code = arg;
    }

    public byte getAgent()
    {
        return this.agent;
    }
    public void setAgent(byte arg)
    {
        this.agent = arg;
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

        code.marshal(writer);

        writer.writeByte(agent);

        writer.writeBytes(content);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            code = Code.unmarshal(reader);
        }

        {
            agent = reader.readByte();
        }

        {
            content = reader.readBytes();
        }

    }


}
