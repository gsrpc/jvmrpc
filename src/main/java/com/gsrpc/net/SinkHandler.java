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

    private final HashedWheelTimer wheelTimer;

    private final ConcurrentHashMap<Integer, Callback> promises = new ConcurrentHashMap<Integer, Callback>();

    private final Dispatcher dispatcher;

    private final Executor taskExecutor;

    private AtomicInteger seqID = new AtomicInteger(0);

    private final AtomicReference<io.netty.channel.Channel> channelRef = new AtomicReference<io.netty.channel.Channel>(null);

    public SinkHandler(Dispatcher dispatcher,Executor taskExecutor,HashedWheelTimer wheelTimer) {
        this.taskExecutor = taskExecutor;
        this.dispatcher = dispatcher;
        this.wheelTimer = wheelTimer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        final Message message = (Message) msg;

        switch (message.getCode()) {
            case Request:

                if(taskExecutor != null) {
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
                } else {
                    handleRequest(message);
                }


                break;
            case Response:

                if (taskExecutor != null) {
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
                } else {
                    handleResponse(message);
                }

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

        final int id = call.getID();

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

        message.setContent(writer.getContent());

        send(message);
    }

    @Override
    public void post(Request call) throws Exception {
        Message message = new Message();

        message.setCode(Code.Request);

        BufferWriter writer = new BufferWriter();

        call.marshal(writer);

        message.setContent(writer.getContent());

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

        Response response = dispatcher.dispatch(request);

        if (response != null) {
            message.setCode(Code.Response);

            BufferWriter writer = new BufferWriter();

            response.marshal(writer);

            message.setContent(writer.getContent());

            send(message);
        }

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
