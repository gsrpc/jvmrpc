package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * IPV4 generate by gs2java,don't modify it manually
 */
public class IPV4
{

    private  byte[] address = new byte[4];



    public byte[] getAddress()
    {
        return this.address;
    }
    public void setAddress(byte[] arg)
    {
        this.address = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)1);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(address);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();
        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                address = reader.readBytes();
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
