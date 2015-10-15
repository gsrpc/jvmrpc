package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


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

        writer.writeUInt16(iD);

        writer.writeUInt16(service);

        writer.writeSByte(exception);

        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {

        iD = reader.readUInt16();

        service = reader.readUInt16();

        exception = reader.readSByte();

        content = reader.readBytes();

    }
}
