package com.gsrpc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by liyang on 15/5/25.
 */
public class BufferReader implements Reader {

    private ByteBuffer content;

    public BufferReader(){

    }

    public  BufferReader(byte[] content) {
        this.content = ByteBuffer.wrap(content);
        this.content.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void setContent(byte[] content) {
        this.content = ByteBuffer.wrap(content);
        this.content.order(ByteOrder.LITTLE_ENDIAN);
    }

    @Override
    public byte ReadByte() throws Exception {
        return content.get();
    }

    @Override
    public byte ReadSByte() throws Exception {
        return content.get();
    }

    @Override
    public short ReadInt16() throws Exception {
        return content.getShort();
    }

    @Override
    public short ReadUInt16() throws Exception {
        return content.getShort();
    }

    @Override
    public int ReadInt32() throws Exception {
        return content.getInt();
    }

    @Override
    public int ReadUInt32() throws Exception {
        return content.getInt();
    }

    @Override
    public long ReadInt64() throws Exception {
        return content.getLong();
    }

    @Override
    public long ReadUint64() throws Exception {
        return content.getLong();
    }

    @Override
    public float ReadFloat32() throws Exception {
        return content.getFloat();
    }

    @Override
    public double ReadFloat64() throws Exception {
        return content.getDouble();
    }

    @Override
    public String ReadString() throws Exception {
        short length = ReadUInt16();

        byte[] buff = new byte[length];


        ReadArrayBytes(buff);

        return new String(buff,"UTF8");
    }

    @Override
    public boolean ReadBoolean() throws Exception {
        byte val=  ReadByte();

        if (val == 1){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public byte[] ReadBytes() throws Exception {
        short length = ReadUInt16();

        byte[] buff = new byte[length];


        ReadArrayBytes(buff);

        return buff;
    }

    @Override
    public void ReadArrayBytes(byte[] val) throws Exception {
        content.get(val);
    }
}