package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class C0 
{

    private  int c1 = 0;

    private  V0 c2 = new V0();

    private  float c3 = 0;



    public C0(){

    }


    public C0(int c1, V0 c2, float c3 ) {
    
        this.c1 = c1;
    
        this.c2 = c2;
    
        this.c3 = c3;
    
    }


    public int getC1()
    {
        return this.c1;
    }
    public void setC1(int arg)
    {
        this.c1 = arg;
    }

    public V0 getC2()
    {
        return this.c2;
    }
    public void setC2(V0 arg)
    {
        this.c2 = arg;
    }

    public float getC3()
    {
        return this.c3;
    }
    public void setC3(float arg)
    {
        this.c3 = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)3);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeInt32(c1);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        c2.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeFloat32(c3);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                c1 = reader.readInt32();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                c2.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                c3 = reader.readFloat32();
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
