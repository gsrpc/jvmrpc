package com.gsrpc.test;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;

import com.gsrpc.Reader;


/*
 * RESTful generate by gs2java,don't modify it manually
 */
public final class RESTfulRPC {

    /**
     * gsrpc net interface
     */
    private com.gsrpc.Channel net;

    /**
     * remote service id
     */
    private short serviceID;

    public RESTfulRPC(com.gsrpc.Channel net, short serviceID){
        this.net = net;
        this.serviceID = serviceID;
    }

    
    public com.gsrpc.Future<Void> Post(String arg0, byte[] arg1, final int timeout) throws Exception {

        com.gsrpc.Request request = new com.gsrpc.Request();

        request.setService(this.serviceID);

        request.setMethod((short)0);

        
        com.gsrpc.Param[] params = new com.gsrpc.Param[2];
		{

			com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

			writer.WriteString(arg0);

			com.gsrpc.Param param = new com.gsrpc.Param();

			param.setContent(writer.Content());

			params[0] = (param);

		}

		{

			com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

			writer.WriteBytes(arg1);

			com.gsrpc.Param param = new com.gsrpc.Param();

			param.setContent(writer.Content());

			params[1] = (param);

		}


        request.setParams(params);
        

        com.gsrpc.Promise<Void> promise = new com.gsrpc.Promise<Void>(timeout){
            @Override
            public void Return(Exception e,com.gsrpc.Response callReturn){

                if (e != null) {
                    Notify(e,null);
                    return;
                }

                try{

                    if(callReturn.getException() != (byte)-1) {
                        switch(callReturn.getException()) {
                            
                            case 0:{
                            com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

                            RemoteException exception = new RemoteException();

                            exception.Unmarshal(reader);

                            Notify(exception,null);

                            return;
                        }
                        
                            case 1:{
                            com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

                            NotFound exception = new NotFound();

                            exception.Unmarshal(reader);

                            Notify(exception,null);

                            return;
                        }
                        
                        default:
                            Notify(new com.gsrpc.RemoteException(String.format("catch unknown exception(%d) for RESTful#Post",callReturn.getException())),null);
                            return;
                        }
                    }

                    
                    Notify(null,null);
                    
                }catch(Exception e1) {
                    Notify(e1,null);
                }
            }
        };

        this.net.send(request,promise);

        return promise;
    }
    
    public com.gsrpc.Future<byte[]> Get(String arg0, final int timeout) throws Exception {

        com.gsrpc.Request request = new com.gsrpc.Request();

        request.setService(this.serviceID);

        request.setMethod((short)1);

        
        com.gsrpc.Param[] params = new com.gsrpc.Param[1];
		{

			com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

			writer.WriteString(arg0);

			com.gsrpc.Param param = new com.gsrpc.Param();

			param.setContent(writer.Content());

			params[0] = (param);

		}


        request.setParams(params);
        

        com.gsrpc.Promise<byte[]> promise = new com.gsrpc.Promise<byte[]>(timeout){
            @Override
            public void Return(Exception e,com.gsrpc.Response callReturn){

                if (e != null) {
                    Notify(e,null);
                    return;
                }

                try{

                    if(callReturn.getException() != (byte)-1) {
                        switch(callReturn.getException()) {
                            
                            case 0:{
                            com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

                            NotFound exception = new NotFound();

                            exception.Unmarshal(reader);

                            Notify(exception,null);

                            return;
                        }
                        
                        default:
                            Notify(new com.gsrpc.RemoteException(String.format("catch unknown exception(%d) for RESTful#Get",callReturn.getException())),null);
                            return;
                        }
                    }

                    
					byte[] returnParam = new byte[0];

					{

						com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

						returnParam = reader.ReadBytes();

					}


                    Notify(null,returnParam);
                    
                }catch(Exception e1) {
                    Notify(e1,null);
                }
            }
        };

        this.net.send(request,promise);

        return promise;
    }
    
}
