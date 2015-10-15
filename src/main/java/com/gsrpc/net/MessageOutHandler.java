package com.gsrpc.net;

import com.gsrpc.BufferWriter;
import com.gsrpc.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * rpc message out handler
 *
 * encode message as output stream bytes
 */
public class MessageOutHandler extends MessageToByteEncoder<Message> {

    private BufferWriter writer = new BufferWriter();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        writer.reset();

        msg.marshal(writer);

        byte[] content = writer.Content();

        writer.reset();

        writer.writeUInt16((short) (content.length & 0xffff));

        out.writeBytes(writer.Content());

        out.writeBytes(content);
    }
}
