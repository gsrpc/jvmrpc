package com.gsrpc.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.HashedWheelTimer;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * The {@link TCPClient} builder
 */
public final class TCPClientBuilder {

    private final Bootstrap bootstrap = new Bootstrap();

    private ChannelInitializer<SocketChannel> handler;

    private final RemoteResolver resolver;

    private int relay = -1;

    private TimeUnit unit;

    private EventLoopGroup eventLoopGroup;

    private Executor taskExecutor;

    private HashedWheelTimer wheelTimer;

    public TCPClientBuilder(RemoteResolver resolver) {

        this.resolver = resolver;

        bootstrap.channel(NioSocketChannel.class);
    }

    public TCPClientBuilder group(EventLoopGroup group) {

        eventLoopGroup = group;
        bootstrap.group(eventLoopGroup);

        return this;
    }


    /**
     * set the dispatchers executor
     * @param executor dispatcher executor
     * @return the tcp client factory object self
     */
    public TCPClientBuilder dispacherExecutor(Executor executor) {
        this.taskExecutor = executor;
        return this;
    }

    /**
     * set customer hashed wheel timer
     * @param hashedWheelTimer customer hashed wheel timer
     * @return the tcp client factory object self
     */
    public TCPClientBuilder wheelTimer(HashedWheelTimer hashedWheelTimer) {

        this.wheelTimer = hashedWheelTimer;

        return this;
    }

    /**
     * set the tcp connection option
     * @param option option
     * @param flag flag
     * @return the tcp client factory object self
     */
    public <T> TCPClientBuilder option(ChannelOption<T> option, T flag) {
        bootstrap.option(option, flag);
        return this;
    }

    public TCPClientBuilder handler(ChannelInitializer<SocketChannel> handler) {

        this.handler = handler;

        return this;
    }

    public TCPClientBuilder reconnect(int relay, TimeUnit unit) {
        this.relay = relay;
        this.unit = unit;
        return this;
    }

    /**
     * create new tcp client
     * @return new tcp client object
     */
    public synchronized TCPClient build() {
        final TCPClient client = new TCPClient(bootstrap, resolver,this.relay,this.unit);


        if (eventLoopGroup == null) {
            eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup);
        }

        if (wheelTimer == null) {
            wheelTimer = HashedWheelTimerSingleton.instance();
        }

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {


            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new LoggingHandler(LogLevel.TRACE));

                ch.pipeline().addLast(new MessageInHandler(), new MessageOutHandler());

                if (relay != -1) {
                    ch.pipeline().addLast(new ReconnectHandler(client, relay, unit));
                }

                if (handler != null) {
                    ch.pipeline().addLast(handler);
                }

                ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        SinkHandler handler = new SinkHandler(client,client,taskExecutor,wheelTimer);

                        ch.pipeline().addLast("sink", handler);
                    }
                });
            }
        });

        return client;
    }

    /**
     * create new tcp client,this method was deprecated using build method instead
     * @return new tcp client object
     */
    @Deprecated
    public synchronized TCPClient Build() {

        return build();
    }
}
