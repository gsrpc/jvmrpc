package com.gsrpc;

import java.nio.ByteBuffer;

import com.gsrpc.Device;

import com.gsrpc.Message;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


public class TunnelWhoAmI 
{

    private  NamedService[] services = new NamedService[0];



    public TunnelWhoAmI(){

    }


    public TunnelWhoAmI(NamedService[] services ) {
    
        this.services = services;
    
    }


    public NamedService[] getServices()
    {
        return this.services;
    }
    public void setServices(NamedService[] arg)
    {
        this.services = arg;
    }



    public void marshal(Writer writer)  throws Exception
    {

        writer.writeUInt16((short)services.length);

		for(NamedService v3 : services){

			v3.marshal(writer);

		}

    }

    public void unmarshal(Reader reader) throws Exception
    {

        {
            int max3 = reader.readUInt16();

		services = new NamedService[max3];

		for(int i3 = 0; i3 < max3; i3 ++ ){

			NamedService v3 = new NamedService();

			v3.unmarshal(reader);

			services[i3] = v3;

		}
        }

    }


}
