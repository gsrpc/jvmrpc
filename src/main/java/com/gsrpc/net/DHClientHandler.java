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
 * DH encode/decode handler for client channel
 */
public class DHClientHandler extends MessageToMessageCodec<Message, Message> {

    /**
     * client device id
     */
    private final Device device;

    /**
     * dh exchange key resolver see {@link DHKeyResolver}
     */
    private final DHKeyResolver resolver;

    /**
     * the channel context dh exchange key see {@link DHKey}
     */
    private DHKey dhKey;

    /**
     * encode cipher
     */
    private Cipher encoder;

    /**
     * decode cipher
     */
    private Cipher decoder;

    /**
     * create new DH handler for client channel
     *
     * @param device   client id
     * @param resolver dh key resolver
     */
    public DHClientHandler(Device device, DHKeyResolver resolver) {

        this.device = device;

        this.resolver = resolver;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);

        WhoAmI whoAmI = new WhoAmI();

        whoAmI.setID(this.device);

        dhKey = resolver.resolver(device);

        BigInteger sendE = dhKey.Exchange();

        whoAmI.setContext(sendE.toString().getBytes("UTF8"));

        Message message = new Message();

        message.setCode(Code.WhoAmI);

        BufferWriter writer = new BufferWriter();

        whoAmI.Marshal(writer);

        message.setContent(writer.Content());

        ctx.channel().writeAndFlush(message);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        if (encoder == null) {
            list.add(message);
            return;
        }

        if (message.getContent() != null && message.getContent().length != 0) {
            message.setContent(encoder.doFinal(message.getContent()));
        }

        list.add(message);

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {

        if (decoder == null) {

            if (message.getCode() != Code.Accept) {
                throw new DHException("DH exchange handshake error,got response " + message.getCode());
            }

            BigInteger E = new BigInteger(new String(message.getContent(), "UTF-8"));

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

            return;
        }

        message.setContent(decoder.doFinal(message.getContent()));

        list.add(message);
    }
}
