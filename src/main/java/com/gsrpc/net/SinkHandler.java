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
public class SinkHandler extends ChannelInboundHandlerAdapter implements MessageChannel {

    private static final Logger logger = LoggerFactory.getLogger(SinkHandler.class);

    private static final HashedWheelTimer wheelTimer = new HashedWheelTimer();

    private final ConcurrentHashMap<Integer, Callback> promises = new ConcurrentHashMap<Integer, Callback>();


    private final StateListener stateListener;

    private final Dispatcher dispatcher;

    private final Executor taskExecutor;

    private AtomicInteger seqID = new AtomicInteger(0);

    private final AtomicReference<io.netty.channel.Channel> channelRef = new AtomicReference<io.netty.channel.Channel>(null);


    public SinkHandler(StateListener stateListener,Dispatcher dispatcher) {

        this.stateListener = stateListener;
        this.dispatcher = dispatcher;

        this.taskExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public SinkHandler(StateListener stateListener,Dispatcher dispatcher,Executor taskExecutor) {
        this.stateListener = stateListener;
        this.taskExecutor = taskExecutor;
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        channelRef.set(ctx.channel());

        if (this.stateListener != null) {
            this.stateListener.stateChanged(State.Connected);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);

        channelRef.set(null);

        if (this.stateListener != null) {
            this.stateListener.stateChanged(State.Closed);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        final Message message = (Message) msg;

        switch (message.getCode()) {
            case Request:

                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleRequest(message);
                        } catch (Exception e) {
                            logger.error("handle request error",e);
                        }
                    }
                });
                break;
            case Response:

                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleResponse(message);
                        } catch (Exception e) {
                            logger.error("handle response error", e);
                        }
                    }
                });

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

        callback.setTimer(wheelTimer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                if (promises.remove((int)id, callback)) {
                    //callback.Return(new TimeoutException("rpc timeout"), null);
                }
            }
        }, callback.getTimeout(), TimeUnit.SECONDS));

        Message message = new Message();

        message.setCode(Code.Request);

        BufferWriter writer = new BufferWriter();

        call.marshal(writer);

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

        request.unmarshal(new BufferReader(message.getContent()));

        Response response = dispatcher.Dispatch(request);

        message.setCode(Code.Response);

        BufferWriter writer = new BufferWriter();

        response.marshal(writer);

        message.setContent(writer.Content());

        send(message);
    }

    private void handleResponse(Message message) throws Exception{

        Response response = new Response();

        response.unmarshal(new BufferReader(message.getContent()));

        Callback callback = promises.remove((int) response.getID());

        if(callback != null) {

            callback.getTimer().cancel();

            callback.Return(null,response);
        }


    }
}
