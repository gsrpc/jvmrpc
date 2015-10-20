package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


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
        writer.writeByte((byte)4);

        writer.writeByte((byte)com.gsrpc.Tag.I16.getValue());
        writer.writeUInt16(iD);

        writer.writeByte((byte)com.gsrpc.Tag.I16.getValue());
        writer.writeUInt16(method);

        writer.writeByte((byte)com.gsrpc.Tag.I16.getValue());
        writer.writeUInt16(service);

        writer.writeByte((byte)((com.gsrpc.Tag.Table.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeUInt16((short)params.length);

		for(Param v3 : params){

			v3.marshal(writer);

		}

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
                method = reader.readUInt16();
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
                int max3 = reader.readUInt16();

		params = new Param[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			Param v3 = new Param();

			v3.unmarshal(reader);

			params[i3] = v3;

		}
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
