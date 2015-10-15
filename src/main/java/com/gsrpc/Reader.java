package com.gsrpc;

/**
 * gsrpc Reader interface
 */
public interface Reader {

    byte readByte() throws Exception;

    byte readSByte() throws Exception;

    short readInt16() throws Exception;

    short readUInt16() throws Exception;

    int readInt32() throws Exception;

    int readUInt32() throws Exception;

    long readInt64() throws Exception;

    long readUInt64() throws Exception;

    float readFloat32() throws Exception;

    double readFloat64() throws Exception;

    String readString() throws Exception;

    boolean readBoolean() throws Exception;

    byte[] readBytes() throws Exception;

    void readArrayBytes(byte[] buff) throws Exception;

}
