package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * ArchType generate by gs2java,don't modify it manually
 */
public enum ArchType {
    X86((byte)0),
	X64((byte)1),
	ARM((byte)2);
    private byte value;
    ArchType(byte val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "X86";
        
        case 1:
            return "X64";
        
        case 2:
            return "ARM";
        
        }
        return "ArchType#" + this.value;
    }
    public byte getValue() {
        return this.value;
    }
    public void marshal(Writer writer) throws Exception
    {
         writer.writeByte(getValue()); 
    }
    public static ArchType unmarshal(Reader reader) throws Exception
    {
        byte code =   reader.readByte(); 
        switch(code)
        {
        
        case 0:
            return ArchType.X86;
        
        case 1:
            return ArchType.X64;
        
        case 2:
            return ArchType.ARM;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
