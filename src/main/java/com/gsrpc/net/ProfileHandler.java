package com.gsrpc.net;


import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * gsrpc profile handler
 */
public class ProfileHandler extends ChannelDuplexHandler {

    private static long timestamp = System.currentTimeMillis();

    private static AtomicInteger pipelines = new AtomicInteger(0);

    private static AtomicInteger received = new AtomicInteger(0);

    private static AtomicInteger sends = new AtomicInteger(0);

    private static AtomicInteger receivedDuration = new AtomicInteger(0);

    private static AtomicInteger sendsDuration = new AtomicInteger(0);

    private static AtomicInteger errors = new AtomicInteger(0);

    private static final String[] titles = new String[]{
            "pipeline", "recv", "send", "errors", "speed-r(op/s)", "speed-s(op/s)"
    };

    private static final int columnSize = 18;


    public static String printProfile() {

        int sendV = sendsDuration.get();

        int receivedV = receivedDuration.get();

        sendsDuration.set(0);

        receivedDuration.set(0);

        long now = System.currentTimeMillis();

        long duration = now - timestamp;

        StringBuilder stringBuilder = new StringBuilder();

        for(String tile : titles) {
            stringBuilder.append(tile);

            for(int i=0; i < columnSize - tile.length(); i ++ ) {
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append("\n");

        String val = String.format("%d",pipelines.get());

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }

        val = String.format("%d",received.get());

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }

        val = String.format("%d",sends.get());

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }

        val = String.format("%d",errors.get());

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }

        val = String.format("%d",errors.get());

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }


        val = String.format("%f",((double)(receivedV)) * 1000 / duration );

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }

        val = String.format("%f",((double)(sendV)) * 1000 / duration );

        stringBuilder.append(val);

        for(int i=0; i < columnSize - val.length(); i ++ ) {
            stringBuilder.append(" ");
        }


        return stringBuilder.toString();
    }


    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);

        received.getAndAdd(1);

        receivedDuration.getAndAdd(1);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);

        sends.getAndAdd(1);

        sendsDuration.getAndAdd(1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        pipelines.getAndAdd(1);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        pipelines.getAndAdd(-1);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        errors.getAndAdd(1);
    }
}
