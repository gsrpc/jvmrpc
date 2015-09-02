package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * Code generate by gs2java,don't modify it manually
 */
public enum Code {
    Heartbeat((byte)0),
	WhoAmI((byte)1),
	Request((byte)2),
	Response((byte)3),
	Accept((byte)4),
	Reject((byte)5),
	Tunnel((byte)6);
    private byte value;
    Code(byte val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "Heartbeat";
        
        case 1:
            return "WhoAmI";
        
        case 2:
            return "Request";
        
        case 3:
            return "Response";
        
        case 4:
            return "Accept";
        
        case 5:
            return "Reject";
        
        case 6:
            return "Tunnel";
        
        }
        return String.format("Code#%d",this.value);
    }
    public byte getValue() {
        return this.value;
    }
    public void Marshal(Writer writer) throws Exception
    {
         writer.WriteByte(getValue()); 
    }
    public static Code Unmarshal(Reader reader) throws Exception
    {
        byte code =   reader.ReadByte(); 
        switch(code)
        {
        
        case 0:
            return Code.Heartbeat;
        
        case 1:
            return Code.WhoAmI;
        
        case 2:
            return Code.Request;
        
        case 3:
            return Code.Response;
        
        case 4:
            return Code.Accept;
        
        case 5:
            return Code.Reject;
        
        case 6:
            return Code.Tunnel;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
