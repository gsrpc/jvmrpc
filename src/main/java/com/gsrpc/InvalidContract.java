package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * InvalidContract generate by gs2java,don't modify it manually
 */
public class InvalidContract
{



    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)0);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();
        
        for(int i = 0; i < (int)__fields; i ++) {
            byte tag = reader.readByte();

            if (tag == com.gsrpc.Tag.Skip.getValue()) {
                continue;
            }

            reader.readSkip(tag);
        }
    }
}
