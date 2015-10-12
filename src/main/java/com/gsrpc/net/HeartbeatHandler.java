package com.gsrpc.net;

import com.gsrpc.Code;
import com.gsrpc.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * heartbeat handler
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private final long relay;


    private final TimeUnit unit;


    private final AtomicReference<Channel> channelRef = new AtomicReference<io.netty.channel.Channel>(null);

    private final Message heartbeat = new Message();

    /**
     * create new reconnect handler for netty
     *
     * @param relay     relay time for heartbeat action
     * @param unit      time unit
     */
    public HeartbeatHandler(long relay, TimeUnit unit) {

        this.relay = relay;
        this.unit = unit;

        heartbeat.setCode(Code.Heartbeat);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelRef.set(ctx.channel());

        sendHeartbeat();
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
    }

    private void sendHeartbeat(){

        final Channel channel = channelRef.get();

        if(channel != null) {
            channel.eventLoop().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {

                    channel.writeAndFlush(heartbeat);
                }
            },0,this.relay,this.unit);
        }
    }
}
