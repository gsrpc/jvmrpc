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

    private ThreadFactory threadFactory = Executors.defaultThreadFactory();

    public TCPClientBuilder(RemoteResolver resolver) {

        this.resolver = resolver;

        bootstrap.channel(NioSocketChannel.class);
    }

    public TCPClientBuilder group(EventLoopGroup group) {

        eventLoopGroup = group;
        bootstrap.group(eventLoopGroup);

        return this;
    }


    public TCPClientBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }


    public TCPClientBuilder option(ChannelOption option, boolean flag) {
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

    public TCPClient Build() throws Exception {

        final TCPClient client = new TCPClient(bootstrap, resolver);


        if (eventLoopGroup == null) {
            eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup);
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

                        SinkHandler handler = new SinkHandler(threadFactory);

                        ch.pipeline().addLast(handler);

                        client.setSink(handler);
                    }
                });
            }
        });

        if (relay != -1) {
            client.reconnect(relay, unit);
        } else {
            client.connect();
        }

        return client;
    }
}
