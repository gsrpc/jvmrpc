package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * gsrpc tcp client
 */
public final class TCPClient implements Reconnect,Sink,StateListener {

    private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);

    private final Bootstrap bootstrap;

    private final RemoteResolver resolver;

    private final int relay;

    private final TimeUnit unit;

    private AtomicReference<State> state = new AtomicReference<State>(State.Disconnect);

    private AtomicReference<Sink> channel = new AtomicReference<Sink>(null);

    private final ConcurrentHashMap<Short,Dispatcher> dispatchers = new ConcurrentHashMap<Short, Dispatcher>();

    private final Promise<Void> connected = new Promise<Void>(0) {
        @Override
        public void Return(Exception e, Response callReturn) {
            this.Notify(e,null);
        }
    };

    private final Promise<Void> closed = new Promise<Void>(0) {
        @Override
        public void Return(Exception e, Response callReturn) {
            this.Notify(e,null);
        }
    };

    TCPClient(Bootstrap bootstrap, RemoteResolver resolver, int relay, TimeUnit unit) {

        this.bootstrap = bootstrap;

        this.resolver = resolver;
        this.relay = relay;
        this.unit = unit;
    }

    public void connect() throws Exception {

        bootstrap.remoteAddress(resolver.Resolve());

        if (relay == -1) {

            if (!state.compareAndSet(State.Disconnect, State.Connecting)) {
                throw new Exception("");
            }

            bootstrap.connect();
        } else {
            reconnect();
        }
    }


    public void close() {
        state.set(State.Closed);

        bootstrap.group().shutdownGracefully();
    }


    void setSink(Sink sink) {

        for(ConcurrentHashMap.Entry<Short,Dispatcher> dispatcher: dispatchers.entrySet()) {
            sink.registerDispatcher(dispatcher.getKey(),dispatcher.getValue());
        }

        this.channel.set(sink);
    }


    @Override
    public void reconnect() throws Exception {

        if (!state.compareAndSet(State.Disconnect, State.Connecting)) {
            throw new Exception("already connected");
        }

        try {

            bootstrap.remoteAddress(resolver.Resolve());

        } catch (Exception e) {
            logger.error("resolver remote address error", e);

            if (state.compareAndSet(State.Connecting, State.Disconnect)) {
                bootstrap.group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reconnect();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }, relay, unit);
            }
        }

        bootstrap.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {

                    if (state.compareAndSet(State.Connecting, State.Disconnect)) {
                        future.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    reconnect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, relay, unit);
                    }


                } else {

                    if (!state.compareAndSet(State.Connecting, State.Connected)) {
                        future.channel().close();
                    }


                    future.channel().closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            state.set(State.Disconnect);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void send(Message message) throws Exception {
        MessageChannel messageChannel = channel.get();

        if(messageChannel == null) {
            throw new BrokenChannel();
        }

        messageChannel.send(message);
    }

    @Override
    public void send(Request call, Callback callback) throws Exception {
        MessageChannel messageChannel = channel.get();

        if(messageChannel == null) {
            throw new BrokenChannel();
        }

        messageChannel.send(call, callback);
    }


    public Future<Void> connected() {
        return this.connected;
    }


    public Future<Void> closed() {
        return this.closed;
    }

    @Override
    public void registerDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.put(id, dispatcher);
    }

    @Override
    public void unregisterDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.remove(id, dispatcher);
    }

    @Override
    public void stateChanged(State state) {
        switch (state){

            case Connected:
                this.connected.Notify(null,null);
                break;
            case Closed:
                this.closed.Notify(null,null);
                break;
        }
    }
}
