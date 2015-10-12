package com.gsrpc.test;


import com.gsrpc.Device;
import com.gsrpc.net.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(BlockJUnit4ClassRunner.class)
public class RPCTest {

    private AtomicInteger id = new AtomicInteger(0);

    private Device createDevice() {
        Device device = new Device();

        device.setID(String.format("%d",id.getAndAdd(1)));

        return device;
    }

    @Test
    public void testConnect() throws Exception {

        TCPClientBuilder builder = new TCPClientBuilder(new RemoteResolver() {
            @Override
            public InetSocketAddress Resolve() throws Exception {
                return new InetSocketAddress("10.0.0.210", 13516);
            }
        }).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new DHClientHandler(createDevice(), new DHKeyResolver() {
                    @Override
                    public DHKey resolver(Device device) throws Exception {
                        return new DHKey(
                                new BigInteger("6849211231874234332173554215962568648211715948614349192108760170867674332076420634857278025209099493881977517436387566623834457627945222750416199306671083"),
                                new BigInteger("13196520348498300509170571968898643110806720751219744788129636326922565480984492185368038375211941297871289403061486510064429072584259746910423138674192557")
                        );
                    }
                }));

                socketChannel.pipeline().addLast(new HeartbeatHandler(5, TimeUnit.SECONDS));

            }
        }).reconnect(2, java.util.concurrent.TimeUnit.SECONDS);


        for(int i = 0; i < 2000; i ++ ) {
            builder.Build();
        }


        Thread.sleep(100000);

    }
}
