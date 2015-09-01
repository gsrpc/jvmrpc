package com.gsrpc.net;

import com.gsrpc.Callback;
import com.gsrpc.Net;
import com.gsrpc.Request;
import com.gsrpc.State;
import com.gsrpc.events.Event;
import com.gsrpc.events.EventListener;
import com.gsrpc.events.Slot;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * gsrpc tcp client
 */
public final class TCPClient extends ChannelInboundHandlerAdapter {

    private final Bootstrap bootstrap;

    private final RemoteResolver resolver;

    private final Event<State> stateEvent = new Event<State>();

    public TCPClient(Bootstrap bootstrap,RemoteResolver resolver) {

        this.bootstrap = bootstrap;

        this.resolver = resolver;

        bootstrap.handler(new ChannelInitializer< SocketChannel>(){

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                
            }
        });
    }

    /**
     * add connection state listener
     * @param listener listener
     * @return listener bound slot
     */
    public Slot addStateListener(EventListener<State> listener) {
        return stateEvent.connect(listener);
    }

    /**
     * connect to remote server
     * @param reconnect if true tcp client will auto reconnect to server when disconnected from server
     */
    public void connect(boolean reconnect) {

    }


}
