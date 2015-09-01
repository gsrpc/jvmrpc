package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.channel.*;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * rpc sink handler
 */
public class SinkHandler extends ChannelInboundHandlerAdapter implements MessageChannel{

    private static final Logger logger = LoggerFactory.getLogger(SinkHandler.class);

    private final HashedWheelTimer wheelTimer;

    private final ConcurrentHashMap<Integer, Callback> promises = new ConcurrentHashMap<Integer, Callback>();

    private final ConcurrentHashMap<Short,Dispatcher> dispatchers = new ConcurrentHashMap<Short, Dispatcher>();

    private AtomicInteger seqID = new AtomicInteger(0);

    private final AtomicReference<io.netty.channel.Channel> channelRef = new AtomicReference<io.netty.channel.Channel>(null);

    /**
     * create new Sink handler with thread factory
     * @param threadFactory a {@link ThreadFactory} that creates a
     *                       background {@link Thread} which is dedicated to
     *                       {@link TimerTask} execution.
     */
    public SinkHandler(ThreadFactory threadFactory) {

        Executors.defaultThreadFactory();

        wheelTimer = new HashedWheelTimer(threadFactory);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        channelRef.set(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);

        channelRef.set(null);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        final Message message = (Message) msg;

        switch (message.getCode()) {
            case Request:

                ctx.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleRequest(message);
                        } catch (Exception e) {
                            logger.error("handle request error",e);
                        }
                    }
                },0,TimeUnit.SECONDS);
                break;
            case Response:

                ctx.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleResponse(message);
                        } catch (Exception e) {
                            logger.error("handle response error", e);
                        }
                    }
                }, 0, TimeUnit.SECONDS);

                break;
            default:
                logger.error("unhandled message({})", message.getCode());
        }
    }

    @Override
    public void send(final Request call, final Callback callback) throws Exception {

        while (true) {
            short id = (short) (int) seqID.addAndGet(1);

            if (null == promises.putIfAbsent((int) id, callback)) {

                call.setID(id);

                break;
            }
        }

        final Short id = call.getID();

        wheelTimer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                if (promises.remove(id, callback)) {
                    callback.Return(new TimeoutException("rpc timeout"), null);
                }
            }
        }, callback.getTimeout(), TimeUnit.SECONDS);

        Message message = new Message();

        message.setCode(Code.Request);

        BufferWriter writer = new BufferWriter();

        call.Marshal(writer);

        message.setContent(writer.Content());

        send(message);
    }

    @Override
    public void send(Message message) throws Exception {

        io.netty.channel.Channel channel = this.channelRef.get();

        if(channel == null){
            throw new BrokenChannel();
        }

        channel.writeAndFlush(message);
    }

    private void handleRequest(Message message) throws Exception{
        Request request = new Request();

        request.Unmarshal(new BufferReader(message.getContent()));

        Dispatcher dispatcher = dispatchers.get(request.getService());

        if (dispatcher != null) {

        }
    }

    private void handleResponse(Message message) throws Exception{

    }
}
