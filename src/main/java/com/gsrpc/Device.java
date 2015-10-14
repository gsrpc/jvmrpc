package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * Device generate by gs2java,don't modify it manually
 */
public class Device
{

    private  String iD = "";

    private  String type = "";

    private  com.gsrpc.ArchType arch = com.gsrpc.ArchType.X86;

    private  com.gsrpc.OSType oS = com.gsrpc.OSType.Windows;

    private  String oSVersion = "";

    private  String appKey = "";



    public String getID()
    {
        return this.iD;
    }
    public void setID(String arg)
    {
        this.iD = arg;
    }

    public String getType()
    {
        return this.type;
    }
    public void setType(String arg)
    {
        this.type = arg;
    }

    public com.gsrpc.ArchType getArch()
    {
        return this.arch;
    }
    public void setArch(com.gsrpc.ArchType arg)
    {
        this.arch = arg;
    }

    public com.gsrpc.OSType getOS()
    {
        return this.oS;
    }
    public void setOS(com.gsrpc.OSType arg)
    {
        this.oS = arg;
    }

    public String getOSVersion()
    {
        return this.oSVersion;
    }
    public void setOSVersion(String arg)
    {
        this.oSVersion = arg;
    }

    public String getAppKey()
    {
        return this.appKey;
    }
    public void setAppKey(String arg)
    {
        this.appKey = arg;
    }

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteString(iD);

        writer.WriteString(type);

        arch.Marshal(writer);

        oS.Marshal(writer);

        writer.WriteString(oSVersion);

        writer.WriteString(appKey);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD = reader.ReadString();

        type = reader.ReadString();

        arch = com.gsrpc.ArchType.Unmarshal(reader);

        oS = com.gsrpc.OSType.Unmarshal(reader);

        oSVersion = reader.ReadString();

        appKey = reader.ReadString();

    }
}
