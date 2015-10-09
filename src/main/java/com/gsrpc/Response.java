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

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteUInt16(iD);

        writer.WriteUInt16(service);

        writer.WriteSByte(exception);

        writer.WriteBytes(content);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD = reader.ReadUInt16();

        service = reader.ReadUInt16();

        exception = reader.ReadSByte();

        content = reader.ReadBytes();

    }
}
