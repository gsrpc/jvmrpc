package com.gsrpc.net;


import com.gsrpc.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * tcp server handler
 */
public final class TCPServer implements Dispatcher {


    private final ServerBootstrap bootstrap;

    private final InetSocketAddress address;

    private AtomicBoolean closed = new AtomicBoolean(false);

    private AtomicReference<ChannelFuture> future = new AtomicReference<ChannelFuture>(null);

    private final ConcurrentHashMap<Short,Dispatcher> dispatchers = new ConcurrentHashMap<Short, Dispatcher>();

    public TCPServer(ServerBootstrap bootstrap, InetSocketAddress address) {

        this.bootstrap = bootstrap;

        this.address = address;
    }

    public void bind(){
        future.set(bootstrap.bind(this.address));
    }

    /**
     * join to server exit event
     */
    public void join() throws InterruptedException {

        ChannelFuture channelFuture = future.get();

        if (channelFuture != null){
            channelFuture.channel().closeFuture().sync();
        }
    }


    /**
     * bind tcp server with auto rebind
     *
     * @param delay rebind delay timeout
     * @param unit  rebind delay timeout unit
     */
    public void bind(final long delay, final TimeUnit unit) {

        future.set(bootstrap.bind(this.address).addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess() && closed.compareAndSet(true, true)) {
                    future.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            bind(delay, unit);
                        }
                    }, delay, unit);
                }
            }
        }));
    }

    public void close() {

        closed.set(true);

        bootstrap.group().shutdownGracefully();

        if(bootstrap.childGroup() != null) {
            bootstrap.childGroup().shutdownGracefully();
        }
    }


    public void registerDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.put(id,dispatcher);
    }


    public void unregisterDispatcher(short id, Dispatcher dispatcher) {
        dispatchers.remove(id,dispatcher);
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
