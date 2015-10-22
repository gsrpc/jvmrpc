package com.gsrpc.net.android;

import com.gsrpc.State;
import com.gsrpc.events.Event;
import com.gsrpc.net.RemoteResolver;

import java.util.concurrent.Executor;

/**
 * Android Gsrpc tcp client
 */
public final class TCPClient {

    /**
     * remove resolver object
     */
    private final RemoteResolver remoteResolver;

    /**
     * client network state event
     */
    private final Event<State> stateEvent;

    /**
     * create new android Gsrpc tcp client
     * @param remoteResolver remote resolver
     * @param executor event executor
     */
    public TCPClient(RemoteResolver remoteResolver,Executor executor){

        this.remoteResolver = remoteResolver;

        this.stateEvent = new Event<>(executor);
    }


    /**
     * invoke async connect method
     */
    public void connect() throws Exception {



    }

}
