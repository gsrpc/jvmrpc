package com.gsrpc.test;


import com.gsrpc.Device;
import com.gsrpc.Promise;
import com.gsrpc.net.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigInteger;
import java.net.InetSocketAddress;

@RunWith(BlockJUnit4ClassRunner.class)
public class RPCTest {

    private TCPClient client;

    private TCPServer server;

    private RESTfulRPC rpc;

    @Before
    public void before() throws Exception {

        server = new TCPServerBuilder(new InetSocketAddress("localhost", 13512))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new DHServerHandler(new DHKeyResolver() {
                            @Override
                            public DHKey resolver(Device device) throws Exception {
                                return new DHKey(
                                        new BigInteger("6849211231874234332173554215962568648211715948614349192108760170867674332076420634857278025209099493881977517436387566623834457627945222750416199306671083"),
                                        new BigInteger("13196520348498300509170571968898643110806720751219744788129636326922565480984492185368038375211941297871289403061486510064429072584259746910423138674192557")
                                );
                            }
                        }));
                    }
                })
                .Build();

        client = new TCPClientBuilder(new RemoteResolver() {
            @Override
            public InetSocketAddress Resolve() throws Exception {
                return new InetSocketAddress("localhost", 13512);
            }
        }).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new DHClientHandler(new Device(), new DHKeyResolver() {
                    @Override
                    public DHKey resolver(Device device) throws Exception {
                        return new DHKey(
                                new BigInteger("6849211231874234332173554215962568648211715948614349192108760170867674332076420634857278025209099493881977517436387566623834457627945222750416199306671083"),
                                new BigInteger("13196520348498300509170571968898643110806720751219744788129636326922565480984492185368038375211941297871289403061486510064429072584259746910423138674192557")
                        );
                    }
                }));
            }
        }).reconnect(2, java.util.concurrent.TimeUnit.SECONDS).Build();


        rpc = new RESTfulRPC(client, (short) 0);

        server.registerDispatcher((short) 0, new RESTfulDispatcher(new MockRESTful()));
    }

    @After
    public void after() throws Exception {
        server.close();
        client.close();
    }


    @Test
    public void testConnect() throws Exception {

        Thread.sleep(4000);

        rpc.Post("hello", "world".getBytes("UTF-8"), 5).util();

        Assert.assertEquals(new String(rpc.Get("hello", 5).util(), "UTF-8"), "world");

    }
}
