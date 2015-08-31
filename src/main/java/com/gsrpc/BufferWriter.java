package com.gsrpc;

import java.io.ByteArrayOutputStream;


public class BufferWriter implements Writer {

    private ByteArrayOutputStream content = new ByteArrayOutputStream();

    public byte[] Content() {
        return this.content.toByteArray();
    }



    @Override
    public void WriteByte(byte val) throws Exception {
        content.write(val);
    }

    @Override
    public void WriteSByte(byte val) throws Exception {
        content.write(val);
    }

    @Override
    public void WriteInt16(short val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));
    }

    @Override
    public void WriteUInt16(short val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));
    }

    @Override
    public void WriteInt32(int val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));
    }

    @Override
    public void WriteUInt32(int val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));

    }

    @Override
    public void WriteInt64(long val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));

        content.write((byte) ((val >> 32) & 0xff));

        content.write((byte) ((val >> 40) & 0xff));

        content.write((byte) ((val >> 48) & 0xff));

        content.write((byte) ((val >> 56) & 0xff));
    }

    @Override
    public void WriteUint64(long val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));

        content.write((byte) ((val >> 32) & 0xff));

        content.write((byte) ((val >> 40) & 0xff));

        content.write((byte) ((val >> 48) & 0xff));

        content.write((byte) ((val >> 56) & 0xff));
    }

    @Override
    public void WriteFloat32(float val) throws Exception {
        WriteInt32(Float.floatToIntBits(val));
    }

    @Override
    public void WriteFloat64(double val) throws Exception {

        WriteInt64(Double.doubleToLongBits(val));

    }

    @Override
    public void WriteString(String val) throws Exception {
        byte[] content = val.getBytes("UTF8");

        WriteUInt16((short) content.length);

        this.content.write(content);
    }

    @Override
    public void WriteArrayBytes(byte[] val) throws Exception {
        this.content.write(val);

    }

    @Override
    public void WriteBytes(byte[] val) throws Exception {
        WriteUInt16((short) val.length);
        WriteArrayBytes(val);
    }

    @Override
    public void WriteBoolean(boolean val) throws Exception {
        if (val){
            WriteByte((byte)1);
        } else {
            WriteByte((byte)0);
        }
    }
}