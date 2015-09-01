package com.gsrpc.test;

import com.gsrpc.Writer;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;



/*
 * RESTful generate by gs2java,don't modify it manually
 */
public final class RESTfulDispatcher implements com.gsrpc.Dispatcher {

    private RESTful service;

    public RESTfulDispatcher(RESTful service) {
        this.service = service;
    }

    public com.gsrpc.Response Dispatch(com.gsrpc.Request call) throws Exception
    {
        switch(call.getMethod()){
        
        case 0: {
				String arg0 = "";

				{

					com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(call.getParams()[0].getContent());

					arg0 = reader.ReadString();

				}

				byte[] arg1 = new byte[0];

				{

					com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(call.getParams()[1].getContent());

					arg1 = reader.ReadBytes();

				}



                try{
                    this.service.Post(arg0, arg1);

                    com.gsrpc.Response callReturn = new com.gsrpc.Response();
                    callReturn.setID(call.getID());
                    callReturn.setService(call.getService());
                    callReturn.setException((byte)-1);

                    

                    return callReturn;

                } catch(Exception e){
                    
                    if(e instanceof RemoteException){

                        com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

                        ((RemoteException)e).Marshal(writer);

                        com.gsrpc.Response callReturn = new com.gsrpc.Response();
                        callReturn.setID(call.getID());
                        callReturn.setService(call.getService());
                        callReturn.setException((byte)0);
                        callReturn.setContent(writer.Content());

                        return callReturn;
                    }
                    
                    if(e instanceof NotFound){

                        com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

                        ((NotFound)e).Marshal(writer);

                        com.gsrpc.Response callReturn = new com.gsrpc.Response();
                        callReturn.setID(call.getID());
                        callReturn.setService(call.getService());
                        callReturn.setException((byte)1);
                        callReturn.setContent(writer.Content());

                        return callReturn;
                    }
                    
                }
            }
        
        case 1: {
				String arg0 = "";

				{

					com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(call.getParams()[0].getContent());

					arg0 = reader.ReadString();

				}



                try{
                    byte[] ret = this.service.Get(arg0);

                    com.gsrpc.Response callReturn = new com.gsrpc.Response();
                    callReturn.setID(call.getID());
                    callReturn.setService(call.getService());
                    callReturn.setException((byte)-1);

                    
    				byte[] returnParam;

				{

					com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

					writer.WriteBytes(ret);

					returnParam = writer.Content();

				}


                    callReturn.setContent(returnParam);
                    

                    return callReturn;

                } catch(Exception e){
                    
                    if(e instanceof NotFound){

                        com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

                        ((NotFound)e).Marshal(writer);

                        com.gsrpc.Response callReturn = new com.gsrpc.Response();
                        callReturn.setID(call.getID());
                        callReturn.setService(call.getService());
                        callReturn.setException((byte)0);
                        callReturn.setContent(writer.Content());

                        return callReturn;
                    }
                    
                }
            }
        
        }
        return null;
    }
}
