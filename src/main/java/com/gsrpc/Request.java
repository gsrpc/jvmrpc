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

    private  Param[] params = null;



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

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteUInt16(iD);

        writer.WriteUInt16(method);

        writer.WriteUInt16(service);

        writer.WriteUInt16((short)params.length);
			for(Param v3 : params){
v3.Marshal(writer);			}

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD = reader.ReadUInt16();

        method = reader.ReadUInt16();

        service = reader.ReadUInt16();

        int imax3 = reader.ReadUInt16();

			params = new Param[imax3];

			for(int i3 = 0; i3 < imax3; i3 ++ ){

				Param v3 = new Param();

				v3.Unmarshal(reader);
				params[i3] = v3;

			}

    }
}
