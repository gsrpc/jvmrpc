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
    public byte readByte() throws Exception {
        return content.get();
    }

    @Override
    public byte readSByte() throws Exception {
        return content.get();
    }

    @Override
    public short readInt16() throws Exception {
        return content.getShort();
    }

    @Override
    public short readUInt16() throws Exception {
        return content.getShort();
    }

    @Override
    public int readInt32() throws Exception {
        return content.getInt();
    }

    @Override
    public int readUInt32() throws Exception {
        return content.getInt();
    }

    @Override
    public long readInt64() throws Exception {
        return content.getLong();
    }

    @Override
    public long readUInt64() throws Exception {
        return content.getLong();
    }

    @Override
    public float readFloat32() throws Exception {
        return content.getFloat();
    }

    @Override
    public double readFloat64() throws Exception {
        return content.getDouble();
    }

    @Override
    public String readString() throws Exception {
        short length = readUInt16();

        byte[] buff = new byte[length];


        readArrayBytes(buff);

        return new String(buff,"UTF8");
    }

    @Override
    public boolean readBoolean() throws Exception {
        byte val=  readByte();

        if (val == 1){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public byte[] readBytes() throws Exception {
        short length = readUInt16();

        byte[] buff = new byte[length];


        readArrayBytes(buff);

        return buff;
    }

    @Override
    public void readArrayBytes(byte[] val) throws Exception {
        content.get(val);
    }

    @Override
    public void readSkip(byte tag) throws Exception {

        if(tag == Tag.I8.getValue()) {
            readByte();
        } else if(tag == Tag.I16.getValue()) {
            readUInt16();
        } else if(tag == Tag.I32.getValue()) {
            readUInt32();
        } else if(tag == Tag.I64.getValue()) {
            readUInt64();
        } else if(tag == Tag.Table.getValue()) {
            readUInt16();
        } else if(tag == Tag.String.getValue()) {
            byte fields = readByte(); // read fields counter

            for (int i =0; i < fields; i ++) {
                tag = readByte();
                readSkip(tag);
            }

        } else {
            if ((tag& 0xf) == Tag.List.getValue()) {
                short length = readUInt16();

                tag = (byte)((tag >> 4) & 0xf);

                for (int i =0; i < length; i ++ ) {
                    readSkip(tag);
                }
            }
        }
    }


}
