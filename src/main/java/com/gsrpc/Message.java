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
        writer.writeByte((byte)3);

        writer.writeByte((byte)com.gsrpc.Tag.I8.getValue());
        code.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.I8.getValue());
        writer.writeByte(agent);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                code = Code.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                agent = reader.readByte();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                content = reader.readBytes();
            }

            if(-- __fields == 0) {
                return;
            }
        }



        for(int i = 0; i < (int)__fields; i ++) {
            byte tag = reader.readByte();

            if (tag == com.gsrpc.Tag.Skip.getValue()) {
                continue;
            }

            reader.readSkip(tag);
        }
    }

}
