package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


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

        writer.writeUInt16(iD);

        writer.writeUInt16(service);

        writer.writeSByte(exception);

        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        iD = reader.readUInt16();
        if(-- __fields == 0) {
            return;
        }

        service = reader.readUInt16();
        if(-- __fields == 0) {
            return;
        }

        exception = reader.readSByte();
        if(-- __fields == 0) {
            return;
        }

        content = reader.readBytes();
        if(-- __fields == 0) {
            return;
        }

    }
}
