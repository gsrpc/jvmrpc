package com.gsrpc.net;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * netty auto reconnect handler
 */
public class ReconnectHandler extends ChannelInboundHandlerAdapter {

    private final Reconnect reconnect;
    private final long relay;
    private final TimeUnit unit;

    /**
     * create new reconnect handler for netty
     *
     * @param reconnect reconnect object
     * @param relay     relay time for reconnect action
     * @param unit      time unit
     */
    public ReconnectHandler(Reconnect reconnect, long relay, TimeUnit unit) {

        this.reconnect = reconnect;

        this.relay = relay;
        this.unit = unit;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    reconnect.reconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, this.relay, unit);
    }
}
