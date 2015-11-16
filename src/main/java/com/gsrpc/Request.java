package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class Request 
{

    private  int iD = 0;

    private  short service = 0;

    private  short method = 0;

    private  Param[] params = new Param[0];

    private  long trace = 0;

    private  int prev = 0;



    public Request(){

    }


    public Request(int iD, short service, short method, Param[] params, long trace, int prev ) {
    
        this.iD = iD;
    
        this.service = service;
    
        this.method = method;
    
        this.params = params;
    
        this.trace = trace;
    
        this.prev = prev;
    
    }


    public int getID()
    {
        return this.iD;
    }
    public void setID(int arg)
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

    public short getMethod()
    {
        return this.method;
    }
    public void setMethod(short arg)
    {
        this.method = arg;
    }

    public Param[] getParams()
    {
        return this.params;
    }
    public void setParams(Param[] arg)
    {
        this.params = arg;
    }

    public long getTrace()
    {
        return this.trace;
    }
    public void setTrace(long arg)
    {
        this.trace = arg;
    }

    public int getPrev()
    {
        return this.prev;
    }
    public void setPrev(int arg)
    {
        this.prev = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeUInt32(iD);

        writer.writeUInt16(service);

        writer.writeUInt16(method);

        writer.writeUInt16((short)params.length);

		for(Param v3 : params){

			v3.marshal(writer);

		}

        writer.writeUInt64(trace);

        writer.writeUInt32(prev);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            iD = reader.readUInt32();
        }

        {
            service = reader.readUInt16();
        }

        {
            method = reader.readUInt16();
        }

        {
            int max3 = reader.readUInt16();

		params = new Param[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			Param v3 = new Param();

			v3.unmarshal(reader);

			params[i3] = v3;

		}
        }

        {
            trace = reader.readUInt64();
        }

        {
            prev = reader.readUInt32();
        }

    }


}
