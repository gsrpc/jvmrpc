package com.gsrpc;

/**
 * Writer the gsrpc java runtimes's Write stream interface
 */
public interface Writer
{
    void writeByte(byte val) throws Exception;

    void writeSByte(byte val) throws Exception;

    void writeInt16(short val) throws Exception;

    void writeUInt16(short val) throws Exception;

    void writeInt32(int val) throws Exception;

    void writeUInt32(int val) throws Exception;

    void writeInt64(long val) throws Exception;

    void writeUInt64(long val) throws Exception;

    void writeFloat32(float val) throws Exception;

    void writeFloat64(double val) throws Exception;

    void writeString(String val) throws Exception;

    void writeArrayBytes(byte[] val) throws Exception;

    void writeBytes(byte[] val) throws Exception;

    void writeBoolean(boolean val) throws Exception;

}
