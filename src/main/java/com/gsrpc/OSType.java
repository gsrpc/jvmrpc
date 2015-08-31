package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * OSType generate by gs2java,don't modify it manually
 */
public enum OSType {
    Windows((byte)0),
	Linux((byte)1),
	OSX((byte)2),
	WP((byte)3),
	Android((byte)4),
	IOS((byte)5);
    private byte value;
    OSType(byte val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "Windows";
        
        case 1:
            return "Linux";
        
        case 2:
            return "OSX";
        
        case 3:
            return "WP";
        
        case 4:
            return "Android";
        
        case 5:
            return "IOS";
        
        }
        return String.format("OSType#%d",this.value);
    }
    public byte getValue() {
        return this.value;
    }
    public void Marshal(Writer writer) throws Exception
    {
         writer.WriteByte(getValue()); 
    }
    public static OSType Unmarshal(Reader reader) throws Exception
    {
        byte code =   reader.ReadByte(); 
        switch(code)
        {
        
        case 0:
            return OSType.Windows;
        
        case 1:
            return OSType.Linux;
        
        case 2:
            return OSType.OSX;
        
        case 3:
            return OSType.WP;
        
        case 4:
            return OSType.Android;
        
        case 5:
            return OSType.IOS;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
