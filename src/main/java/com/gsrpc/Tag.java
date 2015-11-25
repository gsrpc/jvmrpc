package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * Tag generate by gs2java,don't modify it manually
 */
public enum Tag {
    I8((int)0),
	I16((int)1),
	I32((int)2),
	I64((int)3),
	List((int)4),
	Table((int)5),
	String((int)6),
	Skip((int)7);
    private int value;
    Tag(int val){
        this.value = val;
    }
    @Override
    public String toString() {
        switch(this.value)
        {
        
        case 0:
            return "I8";
        
        case 1:
            return "I16";
        
        case 2:
            return "I32";
        
        case 3:
            return "I64";
        
        case 4:
            return "List";
        
        case 5:
            return "Table";
        
        case 6:
            return "String";
        
        case 7:
            return "Skip";
        
        }
        return "Tag#" + this.value;
    }
    public int getValue() {
        return this.value;
    }
    public void marshal(Writer writer) throws Exception
    {
         writer.writeUInt32(getValue()); 
    }
    public static Tag unmarshal(Reader reader) throws Exception
    {
        int code =   reader.readUInt32(); 
        switch(code)
        {
        
        case 0:
            return Tag.I8;
        
        case 1:
            return Tag.I16;
        
        case 2:
            return Tag.I32;
        
        case 3:
            return Tag.I64;
        
        case 4:
            return Tag.List;
        
        case 5:
            return Tag.Table;
        
        case 6:
            return Tag.String;
        
        case 7:
            return Tag.Skip;
        
        }
        throw new Exception("unknown enum constant :" + code);
    }
}
