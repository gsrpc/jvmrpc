package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class Time 
{

    private  long second = 0;

    private  long nano = 0;



    public Time(){

    }


    public Time(long second, long nano ) {
    
        this.second = second;
    
        this.nano = nano;
    
    }


    public long getSecond()
    {
        return this.second;
    }
    public void setSecond(long arg)
    {
        this.second = arg;
    }

    public long getNano()
    {
        return this.nano;
    }
    public void setNano(long arg)
    {
        this.nano = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeUInt64(second);

        writer.writeUInt64(nano);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            second = reader.readUInt64();
        }

        {
            nano = reader.readUInt64();
        }

    }


}
