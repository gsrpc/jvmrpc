package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Response generate by gs2java,don't modify it manually
 */
public class Response
{

    private  short iD = 0;

    private  short service = 0;

    private  byte exception = 0;

    private  byte[] content = new byte[0];



    public short getID()
    {
        return this.iD;
    }
    public void setID(short arg)
    {
        this.iD = arg;
    }

    public short getService()
    {
        return this.service;
    }
    public void setService(short arg)
    {
        this.service = arg;
    }

    public byte getException()
    {
        return this.exception;
    }
    public void setException(byte arg)
    {
        this.exception = arg;
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
        writer.writeByte((byte)4);

        writer.writeByte((byte)com.gsrpc.Tag.I16.getValue());
        writer.writeUInt16(iD);

        writer.writeByte((byte)com.gsrpc.Tag.I16.getValue());
        writer.writeUInt16(service);

        writer.writeByte((byte)com.gsrpc.Tag.I8.getValue());
        writer.writeSByte(exception);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();
        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                iD = reader.readUInt16();
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                service = reader.readUInt16();
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                exception = reader.readSByte();
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
