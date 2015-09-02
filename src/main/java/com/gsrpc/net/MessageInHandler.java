package com.gsrpc.net;


import com.gsrpc.BufferReader;
import com.gsrpc.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * rpc message in handler
 *
 * decode input stream as rpc message objects
 */
public final class MessageInHandler extends ByteToMessageDecoder {

    private enum ReadState {
        Header,Body
    }

    private ReadState state = ReadState.Header;

    private BufferReader reader = new BufferReader();

    private int length;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (state == ReadState.Header) {
            if(in.readableBytes() < 2) {
                return;
            }

            state = ReadState.Body;

            byte[] buff = new byte[2];

            in.readBytes(buff);

            reader.setContent(buff);

            length = reader.ReadUInt16() & 0xffff;
        }


        if (in.readableBytes() < length) {
            return;
        }

        byte[] buff = new byte[length];

        in.readBytes(buff);

        Message message = new Message();

        reader.setContent(buff);

        message.Unmarshal(reader);

        out.add(message);

        state = ReadState.Header;
    }
}
