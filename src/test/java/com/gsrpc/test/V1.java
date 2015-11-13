package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class V1 
{

    private  int f1 = 0;

    private  float f2 = 0;

    private  byte[] f3 = new byte[0];

    private  String[] f4 = new String[0];

    private  String[] f5 = new String[0];



    public V1(){

    }


    public V1(int f1, float f2, byte[] f3, String[] f4, String[] f5 ) {
    
        this.f1 = f1;
    
        this.f2 = f2;
    
        this.f3 = f3;
    
        this.f4 = f4;
    
        this.f5 = f5;
    
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

    public String[] getF4()
    {
        return this.f4;
    }
    public void setF4(String[] arg)
    {
        this.f4 = arg;
    }

    public String[] getF5()
    {
        return this.f5;
    }
    public void setF5(String[] arg)
    {
        this.f5 = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)5);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeInt32(f1);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeFloat32(f2);

        writer.writeByte((byte)((com.gsrpc.Tag.I8.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeBytes(f3);

        writer.writeByte((byte)((com.gsrpc.Tag.String.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeUInt16((short)f4.length);

		for(String v3 : f4){

			writer.writeString(v3);

		}

        writer.writeByte((byte)((com.gsrpc.Tag.String.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeUInt16((short)f5.length);

		for(String v3 : f5){

			writer.writeString(v3);

		}

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


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                int max3 = reader.readUInt16();

		f4 = new String[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			String v3 = "";

			v3 = reader.readString();

			f4[i3] = v3;

		}
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                for(int i3 = 0; i3 < f5.length; i3 ++ ){

			String v3 = f5[i3];

			v3 = reader.readString();

			f5[i3] = v3;

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
