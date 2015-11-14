package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


public class V0 
{

    private  int f1 = 0;

    private  float f2 = 0;

    private  byte[] f3 = new byte[0];



    public V0(){

    }


    public V0(int f1, float f2, byte[] f3 ) {
    
        this.f1 = f1;
    
        this.f2 = f2;
    
        this.f3 = f3;
    
    }


    public int getF1()
    {
        return this.f1;
    }
    public void setF1(int arg)
    {
        this.f1 = arg;
    }

    public float getF2()
    {
        return this.f2;
    }
    public void setF2(float arg)
    {
        this.f2 = arg;
    }

    public byte[] getF3()
    {
        return this.f3;
    }
    public void setF3(byte[] arg)
    {
        this.f3 = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)3);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeInt32(f1);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeFloat32(f2);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(f3);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                f1 = reader.readInt32();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                f2 = reader.readFloat32();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                f3 = reader.readBytes();
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
