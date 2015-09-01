package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * TimeUnit generate by gs2java,don't modify it manually
 */
public enum TimeUnit {
    Second((byte)0);
    private byte value;
    TimeUnit(byte val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "Second";
        
        }
        return String.format("TimeUnit#%d",this.value);
    }
    public byte getValue() {
        return this.value;
    }
    public void Marshal(Writer writer) throws Exception
    {
         writer.WriteByte(getValue()); 
    }
    public static TimeUnit Unmarshal(Reader reader) throws Exception
    {
        byte code =   reader.ReadByte(); 
        switch(code)
        {
        
        case 0:
            return TimeUnit.Second;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
