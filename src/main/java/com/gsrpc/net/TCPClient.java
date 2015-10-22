package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * gsrpc tcp client
 */
public final class TCPClient implements Reconnect, StateListener, MessageChannel,Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);

    private final Object locker = new Object();

    private final Bootstrap bootstrap;

    private final RemoteResolver resolver;

    private final int relay;

    private final TimeUnit unit;

    private State state = State.Disconnect;

    private io.netty.channel.Channel channel;

    private AtomicReference<SinkHandler> sinkHandler = new AtomicReference<SinkHandler>();

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

        synchronized (locker) {
            doConnect();
        }

    }


    private void doConnect() throws Exception {

        if (state != State.Disconnect) {
            return;
        }

        state = State.Connecting;


        InetSocketAddress remote = null;

        try {
            // resolve remote address
            remote = resolver.Resolve();

        } catch (Exception e) {

            state = State.Disconnect;

            // reconnect
            if (relay != -1) {
                bootstrap.group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connect();
                        } catch (Exception e1) {
                            logger.error("connect exception",e1);
                        }
                    }
                },relay,unit);

                return;

            } else {
                throw e;
            }
        }


        bootstrap.connect(remote).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                synchronized (TCPClient.this) {
                    if(future.isSuccess() && state == State.Connecting) {
                        channel = future.channel();
                        state = State.Connected;
                        sinkHandler.set((SinkHandler) channel.pipeline().get("sink"));
                        return;
                    }

                    future.channel().close();

                    state = State.Disconnect;
                }

                // reconnect
                if (relay != -1) {
                    bootstrap.group().schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                connect();
                            } catch (Exception e) {
                                logger.error("connect exception",e);
                            }
                        }
                    }, relay, unit);
                }

            }
        });
    }


    /**
     * close the tcp client
     */
    public void close() {

        synchronized (locker) {

            if(state == State.Connected) {
                channel.close();
            }

            state = State.Closed;
        }
    }


    @Override
    public void reconnect() throws Exception {
        connect();
    }

    @Override
    public void send(Message message) throws Exception {
        MessageChannel messageChannel = sinkHandler.get();

        if(messageChannel == null) {
            throw new BrokenChannel();
        }

        messageChannel.send(message);
    }

    @Override
    public void send(Request call, Callback callback) throws Exception {
        MessageChannel messageChannel = sinkHandler.get();

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


    public void registerDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.put(id, dispatcher);
    }


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

    @Override
    public Response Dispatch(Request request) throws Exception {

        Dispatcher dispatcher = this.dispatchers.get(request.getService());

        if (dispatcher == null) {
            throw new InvalidContractException();
        }

        return dispatcher.Dispatch(request);
    }
}
