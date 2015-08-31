package com.gsrpc;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;


/*
 * Device generate by gs2java,don't modify it manually
 */
public class Device
{

    private  String iD = "";

    private  String type = "";

    private  ArchType arch = ArchType.X86;

    private  OSType oS = OSType.Windows;

    private  String oSVersion = "";



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

    public ArchType getArch()
    {
        return this.arch;
    }
    public void setArch(ArchType arg)
    {
        this.arch = arg;
    }

    public OSType getOS()
    {
        return this.oS;
    }
    public void setOS(OSType arg)
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

    public void Marshal(Writer writer)  throws Exception
    {

        writer.WriteString(iD);

        writer.WriteString(type);

        arch.Marshal(writer);

        oS.Marshal(writer);

        writer.WriteString(oSVersion);

    }
    public void Unmarshal(Reader reader) throws Exception
    {

        iD = reader.ReadString();

        type = reader.ReadString();

        arch.Unmarshal(reader);

        oS.Unmarshal(reader);

        oSVersion = reader.ReadString();

    }
}
