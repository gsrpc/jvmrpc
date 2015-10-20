package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * WhoAmI generate by gs2java,don't modify it manually
 */
public class WhoAmI
{

    private  Device iD = new Device();

    private  byte[] context = new byte[0];



    public Device getID()
    {
        return this.iD;
    }
    public void setID(Device arg)
    {
        this.iD = arg;
    }

    public byte[] getContext()
    {
        return this.context;
    }
    public void setContext(byte[] arg)
    {
        this.context = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)2);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        iD.marshal(writer);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(context);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();
        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                iD.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                context = reader.readBytes();
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
