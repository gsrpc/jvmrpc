package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * Message generate by gs2java,don't modify it manually
 */
public class Message
{

    private  com.gsrpc.Code code = com.gsrpc.Code.Heartbeat;

    private  byte agent = 0;

    private  byte[] content = new byte[0];



    public com.gsrpc.Code getCode()
    {
        return this.code;
    }
    public void setCode(com.gsrpc.Code arg)
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

    public void Marshal(Writer writer)  throws Exception
    {

        code.Marshal(writer);

        writer.WriteByte(agent);

        writer.WriteBytes(content);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        code = com.gsrpc.Code.Unmarshal(reader);

        agent = reader.ReadByte();

        content = reader.ReadBytes();

    }
}
