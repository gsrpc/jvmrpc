package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * Duration generate by gs2java,don't modify it manually
 */
public class Duration
{

    private  int value = 0;

    private  TimeUnit unit = TimeUnit.Second;



    public int getValue()
    {
        return this.value;
    }
    public void setValue(int arg)
    {
        this.value = arg;
    }

    public TimeUnit getUnit()
    {
        return this.unit;
    }
    public void setUnit(TimeUnit arg)
    {
        this.unit = arg;
    }

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteInt32(value);

        unit.Marshal(writer);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        value = reader.ReadInt32();

        unit.Unmarshal(reader);

    }
}
