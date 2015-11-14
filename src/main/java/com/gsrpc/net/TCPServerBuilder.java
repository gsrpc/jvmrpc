package com.gsrpc.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executor;
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
    private final ServerListener listener;

    private final ServerBootstrap bootstrap = new ServerBootstrap();

    private ChannelHandler handler;

    private EventLoopGroup group;

    private EventLoopGroup childGroup;

    private Executor taskExecutor;

    private HashedWheelTimer wheelTimer;


    public TCPServerBuilder(InetSocketAddress address,ServerListener listener) {

        this.address = address;
        this.listener = listener;

        bootstrap.channel(NioServerSocketChannel.class);
    }

    public TCPServerBuilder dispatcherExecutor(Executor executor) {
        this.taskExecutor = executor;
        return this;
    }

    /**
     * set new acceptor event loop group
     * @param group the new io event loop group
     * @return the TCP server factory object self
     */
    public TCPServerBuilder group(EventLoopGroup group) {

        this.group = group;

        return this;
    }

    /**
     * set new sub rector event loop group
     * @param group new sub rector event loop group
     * @return the TCP server factory object self
     */
    public TCPServerBuilder childGroup(EventLoopGroup group) {

        this.childGroup = group;

        return this;
    }

    /**
     * set tcp connection option
     * @param option new option
     * @param flag new option flag
     * @return the TCP server factory object self
     */
    public <T> TCPServerBuilder option(ChannelOption<T> option, T flag) {
        bootstrap.option(option, flag);
        return this;
    }

    public TCPServerBuilder handler(ChannelHandler handler) {

        this.handler = handler;

        return this;
    }

    /**
     * set reconnect params
     * @param relay reconnect relay duration time
     * @param unit duration unit
     * @return the TCP server factory object self
     */
    public TCPServerBuilder reconnect(int relay, TimeUnit unit) {
        this.relay = relay;
        this.unit = unit;
        return this;
    }

    /**
     * create new tcp server with listen address
     * @param address listen address
     * @return tcp server object
     */
    public synchronized  TCPServer build(InetSocketAddress address) {
        final TCPServer server = new TCPServer(bootstrap, address);


        if (group == null) {
            group = new NioEventLoopGroup();
        }

        if (childGroup != null) {
            bootstrap.group(group, childGroup);
        } else {
            bootstrap.group(group);
        }

        if (wheelTimer == null) {
            wheelTimer = HashedWheelTimerSingleton.instance();
        }

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new LoggingHandler(LogLevel.TRACE));

                ch.pipeline().addLast(new MessageInHandler(), new MessageOutHandler());

                if (handler != null) {
                    ch.pipeline().addLast(handler);
                }

                ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ServerHandler serverHandler = new ServerHandler(listener);

                        SinkHandler handler = new SinkHandler(null,serverHandler,taskExecutor,wheelTimer);

                        serverHandler.setMessageChannel(handler);

                        ch.pipeline().addLast(handler,serverHandler);
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

    /**
     * create tcp server,this method is deprecated use build method instead
     * @return TCP server
     */
    @Deprecated
    public synchronized TCPServer Build() {
        return build(address);
    }

    /**
     * create tcp server
     * @return TCP server
     */
    public synchronized TCPServer build() {
        return build(address);
    }
}
