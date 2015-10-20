package com.gsrpc;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


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

        writer.writeByte((byte)com.gsrpc.Tag.String.getValue());
        writer.writeString(iD);

        writer.writeByte((byte)com.gsrpc.Tag.String.getValue());
        writer.writeString(type);

        writer.writeByte((byte)com.gsrpc.Tag.I8.getValue());
        arch.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.I8.getValue());
        oS.marshal(writer);

        writer.writeByte((byte)com.gsrpc.Tag.String.getValue());
        writer.writeString(oSVersion);

        writer.writeByte((byte)com.gsrpc.Tag.String.getValue());
        writer.writeString(appKey);

    }
    public void unmarshal(Reader reader) throws Exception
    {
        byte __fields = reader.readByte();
        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                iD = reader.readString();
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                type = reader.readString();
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                arch = ArchType.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                oS = OSType.unmarshal(reader);
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                oSVersion = reader.readString();
            }

            if(-- __fields == 0) {
                return;
            }
        }

        
        {
            byte tag = reader.readByte();

            if(tag != com.gsrpc.Tag.Skip.getValue()) {
                appKey = reader.readString();
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
