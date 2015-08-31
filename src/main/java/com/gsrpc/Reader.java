package com.gsrpc;

/**
 * gsrpc Reader interface
 */
public interface Reader {

    byte ReadByte() throws Exception;

    byte ReadSByte() throws Exception;

    short ReadInt16() throws Exception;

    short ReadUint16() throws Exception;

    int ReadInt32() throws Exception;

    int ReadUint32() throws Exception;

    long ReadInt64() throws Exception;

    long ReadUint64() throws Exception;

    float ReadFloat32() throws Exception;

    double ReadFloat64() throws Exception;

    String ReadString() throws Exception;

    boolean ReadBoolean() throws Exception;

    byte[] ReadBytes() throws Exception;

    void ReadArrayBytes(byte[] buff) throws Exception;

}
