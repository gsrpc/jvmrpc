package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



/*
 * DNSResolver generate by gs2java,don't modify it manually
 */
public final class DNSResolverDispatcher implements com.gsrpc.Dispatcher {

    private DNSResolver service;

    public DNSResolverDispatcher(DNSResolver service) {
        this.service = service;
    }

    public com.gsrpc.Response Dispatch(com.gsrpc.Request call) throws Exception
    {
        switch(call.getMethod()){
        
        case 0: {
				String arg0 = "";

				{

					com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(call.getParams()[0].getContent());

					arg0 = reader.readString();

				}


                
                try{
                
                    IPV4 ret = this.service.resolve(arg0);

                    com.gsrpc.Response callReturn = new com.gsrpc.Response();
                    callReturn.setID(call.getID());
                    callReturn.setService(call.getService());
                    callReturn.setException((byte)-1);

                    
    				byte[] returnParam;

				{

					com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

					ret.marshal(writer);

					returnParam = writer.Content();

				}


                    callReturn.setContent(returnParam);
                    

                    return callReturn;

                } catch(UnknownException e) {

                    com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

                    e.marshal(writer);

                    com.gsrpc.Response callReturn = new com.gsrpc.Response();
                    callReturn.setID(call.getID());
                    callReturn.setService(call.getService());
                    callReturn.setException((byte)0);
                    callReturn.setContent(writer.Content());

                    return callReturn;
                } catch(ResourceException e) {

                    com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

                    e.marshal(writer);

                    com.gsrpc.Response callReturn = new com.gsrpc.Response();
                    callReturn.setID(call.getID());
                    callReturn.setService(call.getService());
                    callReturn.setException((byte)1);
                    callReturn.setContent(writer.Content());

                    return callReturn;
                }
            }
        
        }
        return null;
    }
}
