package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class Response 
{

    private  int iD = 0;

    private  byte exception = 0;

    private  byte[] content = new byte[0];

    private  long trace = 0;



    public Response(){

    }


    public Response(int iD, byte exception, byte[] content, long trace ) {
    
        this.iD = iD;
    
        this.exception = exception;
    
        this.content = content;
    
        this.trace = trace;
    
    }


    public int getID()
    {
        return this.iD;
    }
    public void setID(int arg)
    {
        this.iD = arg;
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

    public long getTrace()
    {
        return this.trace;
    }
    public void setTrace(long arg)
    {
        this.trace = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeUInt32(iD);

        writer.writeSByte(exception);

        writer.writeBytes(content);

        writer.writeUInt64(trace);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            iD = reader.readUInt32();
        }

        {
            exception = reader.readSByte();
        }

        {
            content = reader.readBytes();
        }

        {
            trace = reader.readUInt64();
        }

    }


}
