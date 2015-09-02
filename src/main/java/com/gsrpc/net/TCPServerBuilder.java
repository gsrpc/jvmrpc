package com.gsrpc.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * {@link TCPServer} builder
 */
public final class TCPServerBuilder {

    private int relay = -1;

    private TimeUnit unit;

    private final InetSocketAddress address;

    private final ServerBootstrap bootstrap = new ServerBootstrap();

    private ChannelHandler handler;

    private EventLoopGroup group;

    private EventLoopGroup childGroup;

    private ThreadFactory threadFactory = Executors.defaultThreadFactory();

    public TCPServerBuilder(InetSocketAddress address) {

        this.address = address;

        bootstrap.channel(NioServerSocketChannel.class);
    }

    public TCPServerBuilder threadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public TCPServerBuilder group(EventLoopGroup group) {

        this.group = group;

        return this;
    }

    public TCPServerBuilder childGroup(EventLoopGroup group) {

        this.childGroup = group;

        return this;
    }


    public TCPServerBuilder option(ChannelOption option, boolean flag) {
        bootstrap.option(option, flag);
        return this;
    }

    public TCPServerBuilder handler(ChannelHandler handler) {

        this.handler = handler;

        return this;
    }

    public TCPServerBuilder reconnect(int relay, TimeUnit unit) {
        this.relay = relay;
        this.unit = unit;
        return this;
    }

    public TCPServer Build() throws Exception {

        final TCPServer server = new TCPServer(bootstrap, address);


        if (group == null) {
            group = new NioEventLoopGroup();
        }

        if (childGroup != null) {
            bootstrap.group(group, childGroup);
        } else {
            bootstrap.group(group);
        }

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new MessageInHandler(), new MessageOutHandler());

                if (handler != null) {
                    ch.pipeline().addLast(handler);
                }

                ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        SinkHandler handler = new SinkHandler(threadFactory);

                        ch.pipeline().addLast(handler);

                        server.setSink(handler);
                    }
                });
            }
        });

        if (relay != -1) {
            server.bind(relay,unit);
        } else {
            server.bind();
        }

        return server;
    }
}
