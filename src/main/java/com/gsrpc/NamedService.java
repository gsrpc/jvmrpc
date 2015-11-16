package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Device;

import com.gsrpc.Message;

import com.gsrpc.Writer;


public class NamedService 
{

    private  String name = "";

    private  short dispatchID = 0;

    private  int vNodes = 0;

    private  String nodeName = "";



    public NamedService(){

    }


    public NamedService(String name, short dispatchID, int vNodes, String nodeName ) {
    
        this.name = name;
    
        this.dispatchID = dispatchID;
    
        this.vNodes = vNodes;
    
        this.nodeName = nodeName;
    
    }


    public String getName()
    {
        return this.name;
    }
    public void setName(String arg)
    {
        this.name = arg;
    }

    public short getDispatchID()
    {
        return this.dispatchID;
    }
    public void setDispatchID(short arg)
    {
        this.dispatchID = arg;
    }

    public int getVNodes()
    {
        return this.vNodes;
    }
    public void setVNodes(int arg)
    {
        this.vNodes = arg;
    }

    public String getNodeName()
    {
        return this.nodeName;
    }
    public void setNodeName(String arg)
    {
        this.nodeName = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeString(name);

        writer.writeUInt16(dispatchID);

        writer.writeUInt32(vNodes);

        writer.writeString(nodeName);

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            name = reader.readString();
        }

        {
            dispatchID = reader.readUInt16();
        }

        {
            vNodes = reader.readUInt32();
        }

        {
            nodeName = reader.readString();
        }

    }


}
