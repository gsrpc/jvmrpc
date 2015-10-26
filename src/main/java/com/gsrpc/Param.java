package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class Param 
{

    private  byte[] content = new byte[0];



    public Param(){

    }


    public Param(byte[] content ) {
    
        this.content = content;
    
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
        writer.writeByte((byte)1);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(content);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                content = reader.readBytes();
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
