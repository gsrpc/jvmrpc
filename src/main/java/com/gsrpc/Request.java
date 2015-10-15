package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * Request generate by gs2java,don't modify it manually
 */
public class Request
{

    private  short iD = 0;

    private  short method = 0;

    private  short service = 0;

    private  Param[] params = new Param[0];



    public short getID()
    {
        return this.iD;
    }
    public void setID(short arg)
    {
        this.iD = arg;
    }

    public short getMethod()
    {
        return this.method;
    }
    public void setMethod(short arg)
    {
        this.method = arg;
    }

    public short getService()
    {
        return this.service;
    }
    public void setService(short arg)
    {
        this.service = arg;
    }

    public Param[] getParams()
    {
        return this.params;
    }
    public void setParams(Param[] arg)
    {
        this.params = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {

        writer.writeUInt16(iD);

        writer.writeUInt16(method);

        writer.writeUInt16(service);

        writer.writeUInt16((short)params.length);

		for(Param v3 : params){

			v3.marshal(writer);

		}

    }
    public void unmarshal(Reader reader) throws Exception
    {

        iD = reader.readUInt16();

        method = reader.readUInt16();

        service = reader.readUInt16();

        int max3 = reader.readUInt16();

		params = new Param[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			Param v3 = new Param();

			v3.unmarshal(reader);

			params[i3] = v3;

		}

    }
}
