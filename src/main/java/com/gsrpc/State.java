package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * State generate by gs2java,don't modify it manually
 */
public enum State {
    Disconnect((byte)0),
	Connecting((byte)1),
	Connected((byte)2),
	Disconnecting((byte)3),
	Closed((byte)4);
    private byte value;
    State(byte val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "Disconnect";
        
        case 1:
            return "Connecting";
        
        case 2:
            return "Connected";
        
        case 3:
            return "Disconnecting";
        
        case 4:
            return "Closed";
        
        }
        return String.format("State#%d",this.value);
    }
    public byte getValue() {
        return this.value;
    }
    public void Marshal(Writer writer) throws Exception
    {
         writer.WriteByte(getValue()); 
    }
    public static State Unmarshal(Reader reader) throws Exception
    {
        byte code =   reader.ReadByte(); 
        switch(code)
        {
        
        case 0:
            return State.Disconnect;
        
        case 1:
            return State.Connecting;
        
        case 2:
            return State.Connected;
        
        case 3:
            return State.Disconnecting;
        
        case 4:
            return State.Closed;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
