package com.gsrpc.net;

import com.gsrpc.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.List;

/**
 * DH encode/decode handler for server channel
 */
public class DHServerHandler extends MessageToMessageCodec<Message, Message> {
    /**
     * dh exchange key resolver see {@link DHKeyResolver}
     */
    private final DHKeyResolver resolver;

    /**
     * encode cipher
     */
    private Cipher encoder;

    /**
     * decode cipher
     */
    private Cipher decoder;

    /**
     * create new DH handler for server channel
     *
     * @param resolver dh key resolver
     */
    public DHServerHandler(DHKeyResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // skip channel active event
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        if (encoder == null) {
            list.add(message);
            return;
        }

        message.setContent(encoder.doFinal(message.getContent()));

        list.add(message);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {

        if (decoder == null) {

            if (message.getCode() != Code.WhoAmI) {
                throw new DHException("DH exchange handshake error,expect WhoAmI but got message " + message.getCode());
            }

            BufferReader reader = new BufferReader(message.getContent());

            WhoAmI whoAmI = new WhoAmI();

            whoAmI.unmarshal(reader);

            DHKey dhKey = resolver.resolver(whoAmI.getID());

            message.setContent(dhKey.Exchange().toString().getBytes("UTF-8"));

            message.setCode(Code.Accept);

            // send accept message to client
            channelHandlerContext.channel().writeAndFlush(message);

            BigInteger E = new BigInteger(new String(whoAmI.getContext(), "UTF-8"));

            long sharedKey = dhKey.SharedKey(E).longValue();

            ByteBuffer buff = ByteBuffer.allocate(8);

            buff.order(ByteOrder.BIG_ENDIAN);

            buff.putLong(sharedKey);

            encoder = Cipher.getInstance("DES/ECB/PKCS5Padding");

            DESKeySpec dks = new DESKeySpec(buff.array());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            encoder.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(dks), new SecureRandom());

            decoder = Cipher.getInstance("DES/ECB/PKCS5Padding");

            decoder.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(dks), new SecureRandom());

            channelHandlerContext.fireChannelActive();

            return;
        }

        message.setContent(decoder.doFinal(message.getContent()));

        list.add(message);
    }
}
