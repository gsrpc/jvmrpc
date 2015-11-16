package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * the Server Handler
 */
final class ServerHandler extends ChannelInboundHandlerAdapter implements DispatcherChannel {

    private final ServerListener listener;
    private MessageChannel messageChannel;

    private final ConcurrentHashMap<Short,Dispatcher> dispatchers = new ConcurrentHashMap<Short, Dispatcher>();

    public void setMessageChannel(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    public ServerHandler(ServerListener listener) {

        this.listener = listener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);

        this.listener.addClient(this);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);

        this.listener.removeClient(this);
    }

    @Override
    public void addService(short id, Dispatcher dispatcher) {
        dispatchers.put(id,dispatcher);
    }

    @Override
    public void removeService(short id, Dispatcher dispatcher) {
        dispatchers.remove(id,dispatcher);
    }

    @Override
    public void addService(NamedDispatcher dispatcher) throws UnknownServiceException {
        addService(Register.getInstance().getID(dispatcher.name()),dispatcher);
    }

    @Override
    public void removeService(NamedDispatcher dispatcher) throws UnknownServiceException {
        removeService(Register.getInstance().getID(dispatcher.name()),dispatcher);
    }

    @Override
    public Response dispatch(Request request) throws Exception {
        Dispatcher dispatcher = this.dispatchers.get(request.getService());

        if (dispatcher == null) {
            throw new InvalidContractException();
        }

        return dispatcher.dispatch(request);
    }

    @Override
    public void send(Request call, Callback callback) throws Exception {
        this.messageChannel.send(call,callback);
    }

    @Override
    public void post(Request call) throws Exception {
        this.messageChannel.post(call);
    }
}
