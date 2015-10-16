package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Message generate by gs2java,don't modify it manually
 */
public class Message
{

    private  Code code = Code.Heartbeat;

    private  byte agent = 0;

    private  byte[] content = new byte[0];



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
        writer.writeByte((byte)3);

        code.marshal(writer);

        writer.writeByte(agent);

        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        code = Code.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

        agent = reader.readByte();
        if(-- __fields == 0) {
            return;
        }

        content = reader.readBytes();
        if(-- __fields == 0) {
            return;
        }

    }
}
