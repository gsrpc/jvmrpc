package com.gsrpc;

import java.io.ByteArrayOutputStream;


public class BufferWriter implements Writer {

    private ByteArrayOutputStream content = new ByteArrayOutputStream();

    public byte[] Content() {
        return this.content.toByteArray();
    }


    public void reset() {
        content.reset();
    }

    @Override
    public void writeByte(byte val) throws Exception {
        content.write(val);
    }

    @Override
    public void writeSByte(byte val) throws Exception {
        content.write(val);
    }

    @Override
    public void writeInt16(short val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));
    }

    @Override
    public void writeUInt16(short val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));
    }

    @Override
    public void writeInt32(int val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));
    }

    @Override
    public void writeUInt32(int val) throws Exception {
        content.write((byte) (val & 0xff));

        content.write((byte) ((val >> 8) & 0xff));

        content.write((byte) ((val >> 16) & 0xff));

        content.write((byte) ((val >> 24) & 0xff));

    }

    @Override
    public void writeInt64(long val) throws Exception {
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
    public void writeUInt64(long val) throws Exception {
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
    public void writeFloat32(float val) throws Exception {
        writeInt32(Float.floatToIntBits(val));
    }

    @Override
    public void writeFloat64(double val) throws Exception {

        writeInt64(Double.doubleToLongBits(val));

    }

    @Override
    public void writeString(String val) throws Exception {
        byte[] content = val.getBytes("UTF8");

        writeUInt16((short) content.length);

        this.content.write(content);
    }

    @Override
    public void writeArrayBytes(byte[] val) throws Exception {
        this.content.write(val);

    }

    @Override
    public void writeBytes(byte[] val) throws Exception {
        writeUInt16((short) val.length);
        writeArrayBytes(val);
    }

    @Override
    public void writeBoolean(boolean val) throws Exception {
        if (val){
            writeByte((byte) 1);
        } else {
            writeByte((byte) 0);
        }
    }
}
