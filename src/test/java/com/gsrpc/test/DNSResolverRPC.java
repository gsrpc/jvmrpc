package com.gsrpc.test;

import com.gsrpc.Reader;

import java.nio.ByteBuffer;

import com.gsrpc.Writer;


/*
 * DNSResolver generate by gs2java,don't modify it manually
 */
public final class DNSResolverRPC {

    /**
     * gsrpc net interface
     */
    private com.gsrpc.Channel net;

    /**
     * remote service id
     */
    private short serviceID;

    public DNSResolverRPC(com.gsrpc.Channel net, short serviceID){
        this.net = net;
        this.serviceID = serviceID;
    }

    
    public com.gsrpc.Future<IPV4> resolve(String arg0, final int timeout) throws Exception {

        com.gsrpc.Request request = new com.gsrpc.Request();

        request.setService(this.serviceID);

        request.setMethod((short)0);

        
        com.gsrpc.Param[] params = new com.gsrpc.Param[1];
		{

			com.gsrpc.BufferWriter writer = new com.gsrpc.BufferWriter();

			writer.writeString(arg0);

			com.gsrpc.Param param = new com.gsrpc.Param();

			param.setContent(writer.Content());

			params[0] = (param);

		}


        request.setParams(params);
        

        com.gsrpc.Promise<IPV4> promise = new com.gsrpc.Promise<IPV4>(timeout){
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

                            UnknownException exception = new UnknownException();

                            exception.unmarshal(reader);

                            Notify(exception,null);

                            return;
                        }
                        
                            case 1:{
                            com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

                            ResourceException exception = new ResourceException();

                            exception.unmarshal(reader);

                            Notify(exception,null);

                            return;
                        }
                        
                        default:
                            Notify(new com.gsrpc.RemoteException(),null);
                            return;
                        }
                    }

                    
					IPV4 returnParam = new IPV4();

					{

						com.gsrpc.BufferReader reader = new com.gsrpc.BufferReader(callReturn.getContent());

						returnParam.unmarshal(reader);

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
