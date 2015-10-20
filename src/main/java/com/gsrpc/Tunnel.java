package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Tunnel generate by gs2java,don't modify it manually
 */
public class Tunnel
{

    private  Device iD = new Device();

    private  Message message = new Message();



    public Device getID()
    {
        return this.iD;
    }
    public void setID(Device arg)
    {
        this.iD = arg;
    }

    public Message getMessage()
    {
        return this.message;
    }
    public void setMessage(Message arg)
    {
        this.message = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)2);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        iD.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        message.marshal(writer);

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
                message.unmarshal(reader);
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
