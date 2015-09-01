package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Block generate by gs2java,don't modify it manually
 */
public class Block
{

    private  byte[] content = new byte[256];

    private  KV[][] kV = new KV[][128];



    public byte[] getContent()
    {
        return this.content;
    }
    public void setContent(byte[] arg)
    {
        this.content = arg;
    }

    public KV[][] getKV()
    {
        return this.kV;
    }
    public void setKV(KV[][] arg)
    {
        this.kV = arg;
    }

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteseqBytes(content);

        writer.WriteUInt16((short)kV.length);
for(KV[] v3 : kV){
writer.WriteUInt16((short)v3.length);
for(KV v4 : v3){
v4.Marshal(writer);				}			}

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        reader.ReadseqBytes(content);

        int imax3 = reader.ReadUInt16();

			kV = new KV[][imax3];

			for(int i3 = 0; i3 < imax3; i3 ++ ){

				KV[] v3 = new KV[12];

int imax4 = reader.ReadUInt16();

				v3 = new KV[imax4];

				for(int i4 = 0; i4 < imax4; i4 ++ ){

					KV v4 = new KV();

v4.Unmarshal(reader);
					v3[i4] = v4;

				}
				kV[i3] = v3;

			}

    }
}
