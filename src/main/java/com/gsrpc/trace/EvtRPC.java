package com.gsrpc.trace;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.KV;

import com.gsrpc.Time;

import com.gsrpc.Reader;


public class EvtRPC 
{

    private  long trace = 0;

    private  int iD = 0;

    private  int prev = 0;

    private  String probe = "";

    private  Time startTime = new Time();

    private  Time endTime = new Time();

    private  KV[] attributes = new KV[0];



    public EvtRPC(){

    }


    public EvtRPC(long trace, int iD, int prev, String probe, Time startTime, Time endTime, KV[] attributes ) {
    
        this.trace = trace;
    
        this.iD = iD;
    
        this.prev = prev;
    
        this.probe = probe;
    
        this.startTime = startTime;
    
        this.endTime = endTime;
    
        this.attributes = attributes;
    
    }


    public long getTrace()
    {
        return this.trace;
    }
    public void setTrace(long arg)
    {
        this.trace = arg;
    }

    public int getID()
    {
        return this.iD;
    }
    public void setID(int arg)
    {
        this.iD = arg;
    }

    public int getPrev()
    {
        return this.prev;
    }
    public void setPrev(int arg)
    {
        this.prev = arg;
    }

    public String getProbe()
    {
        return this.probe;
    }
    public void setProbe(String arg)
    {
        this.probe = arg;
    }

    public Time getStartTime()
    {
        return this.startTime;
    }
    public void setStartTime(Time arg)
    {
        this.startTime = arg;
    }

    public Time getEndTime()
    {
        return this.endTime;
    }
    public void setEndTime(Time arg)
    {
        this.endTime = arg;
    }

    public KV[] getAttributes()
    {
        return this.attributes;
    }
    public void setAttributes(KV[] arg)
    {
        this.attributes = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)7);

        writer.writeByte((byte)com.gsrpc.Tag.I64.getValue());
        writer.writeUInt64(trace);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeUInt32(iD);

        writer.writeByte((byte)com.gsrpc.Tag.I32.getValue());
        writer.writeUInt32(prev);

        writer.writeByte((byte)com.gsrpc.Tag.String.getValue());
        writer.writeString(probe);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        startTime.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.Table.getValue());
        endTime.marshal(writer);

        writer.writeByte((byte)((com.gsrpc.Tag.Table.getValue() << 4)|com.gsrpc.Tag.List.getValue()));
        writer.writeUInt16((short)attributes.length);

		for(KV v3 : attributes){

			v3.marshal(writer);

		}

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                trace = reader.readUInt64();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                iD = reader.readUInt32();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                prev = reader.readUInt32();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                probe = reader.readString();
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                startTime.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                endTime.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }


        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                int max3 = reader.readUInt16();

		attributes = new KV[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			KV v3 = new KV();

			v3.unmarshal(reader);

			attributes[i3] = v3;

		}
            }

            if(-- __fields == 0) {
                return;
            }
        }



        for(int i = 0; i < (int)__fields; i ++) {
            byte tag = reader.readByte();

            if (tag == com.gsrpc.Tag.Skip.getValue()) {
                continue;
            }

            reader.readSkip(tag);
        }
    }

}
