package com.gsrpc.net;

import com.gsrpc.Code;
import com.gsrpc.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * heartbeat handler
 */
public class HeartbeatEchoHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatEchoHandler.class);

    /**
     * the heartbeat wheel timer singlton
     */
    private static final HashedWheelTimer wheelTimer = new HashedWheelTimer();

    private final long relay;


    private final TimeUnit unit;


    private final AtomicReference<Channel> channelRef = new AtomicReference<io.netty.channel.Channel>(null);

    /**
     * create new reconnect handler for netty
     *
     * @param relay     relay time for heartbeat action
     * @param unit      time unit
     */
    public HeartbeatEchoHandler(long relay, TimeUnit unit) {

        this.relay = relay;
        this.unit = unit;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        channelRef.set(null);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Message message = (Message)msg;

        if(message.getCode() != Code.Heartbeat) {
            ctx.fireChannelRead(msg);
        }

        ctx.channel().writeAndFlush(msg);
    }
}
