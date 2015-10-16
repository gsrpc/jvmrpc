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

    public String getAppKey()
    {
        return this.appKey;
    }
    public void setAppKey(String arg)
    {
        this.appKey = arg;
    }

    public void marshal(Writer writer)  throws Exception
    {
        writer.writeByte((byte)6);

        writer.writeString(iD);

        writer.writeString(type);

        arch.marshal(writer);

        oS.marshal(writer);

        writer.writeString(oSVersion);

        writer.writeString(appKey);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();

        iD = reader.readString();
        if(-- __fields == 0) {
            return;
        }

        type = reader.readString();
        if(-- __fields == 0) {
            return;
        }

        arch = ArchType.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

        oS = OSType.unmarshal(reader);
        if(-- __fields == 0) {
            return;
        }

        oSVersion = reader.readString();
        if(-- __fields == 0) {
            return;
        }

        appKey = reader.readString();
        if(-- __fields == 0) {
            return;
        }

    }
}
