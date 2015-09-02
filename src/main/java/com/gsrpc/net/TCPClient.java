package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * gsrpc tcp client
 */
public final class TCPClient implements Reconnect,Sink {

    private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);

    private final Bootstrap bootstrap;

    private final RemoteResolver resolver;

    private AtomicReference<State> state = new AtomicReference<State>(State.Disconnect);

    private AtomicReference<Sink> channel = new AtomicReference<Sink>(null);

    private final ConcurrentHashMap<Short,Dispatcher> dispatchers = new ConcurrentHashMap<Short, Dispatcher>();

    TCPClient(Bootstrap bootstrap, RemoteResolver resolver) {

        this.bootstrap = bootstrap;

        this.resolver = resolver;
    }

    public void connect() throws Exception {

        if (!state.compareAndSet(State.Disconnect, State.Connecting)) {
            return;
        }

        bootstrap.remoteAddress(resolver.Resolve());

        bootstrap.connect();
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
    public void reconnect(final long relay, final TimeUnit unit) {

        if (!state.compareAndSet(State.Disconnect, State.Connecting)) {
            return;
        }

        try {

            bootstrap.remoteAddress(resolver.Resolve());

        } catch (Exception e) {
            logger.error("resolver remote address error", e);

            if (state.compareAndSet(State.Connecting, State.Disconnect)) {
                bootstrap.group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        reconnect(relay, unit);
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
                                reconnect(relay, unit);
                            }
                        }, relay, unit);
                    }


                } else {
                    if (!state.compareAndSet(State.Connecting, State.Connected)) {
                        future.channel().close();
                    }
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

    @Override
    public void registerDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.put(id, dispatcher);
    }

    @Override
    public void unregisterDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.remove(id, dispatcher);
    }
}
