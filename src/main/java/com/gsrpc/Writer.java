package com.gsrpc;

/**
 * Writer the gsrpc java runtimes's Write stream interface
 */
public interface Writer
{
    void WriteByte(byte val) throws Exception;

    void WriteSByte(byte val) throws Exception;

    void WriteInt16(short val) throws Exception;

    void WriteUInt16(short val) throws Exception;

    void WriteInt32(int val) throws Exception;

    void WriteUInt32(int val) throws Exception;

    void WriteInt64(long val) throws Exception;

    void WriteUInt64(long val) throws Exception;

    void WriteFloat32(float val) throws Exception;

    void WriteFloat64(double val) throws Exception;

    void WriteString(String val) throws Exception;

    void WriteArrayBytes(byte[] val) throws Exception;

    void WriteBytes(byte[] val) throws Exception;

    void WriteBoolean(boolean val) throws Exception;

}
