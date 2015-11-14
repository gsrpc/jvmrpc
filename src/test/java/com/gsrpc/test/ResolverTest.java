package com.gsrpc.test;


import com.gsrpc.BufferReader;
import com.gsrpc.BufferWriter;
import com.gsrpc.Device;
import com.gsrpc.DispatcherChannel;
import com.gsrpc.net.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(BlockJUnit4ClassRunner.class)
public class ResolverTest {

    private final TCPServer tcpServer;

    private final TCPClientBuilder tcpClientBuilder;

    public ResolverTest() throws Exception {

        // create server builder factory
        tcpServer = new TCPServerBuilder(
                new InetSocketAddress("localhost", 5120),
                new ServerListener() {
                    @Override
                    public void addClient(DispatcherChannel dispatcherChannel) {
                        dispatcherChannel.addService( (short)0,new DNSResolverDispatcher(new MockDNSResolver(dispatcherChannel)));
                    }

                    @Override
                    public void removeClient(DispatcherChannel dispatcherChannel) {

                    }
                }
        ).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DHServerHandler(new DHKeyResolver() {
                    @Override
                    public DHKey resolver(Device device) throws Exception {
                        return new DHKey(
                                new BigInteger("6849211231874234332173554215962568648211715948614349192108760170867674332076420634857278025209099493881977517436387566623834457627945222750416199306671083"),
                                new BigInteger("13196520348498300509170571968898643110806720751219744788129636326922565480984492185368038375211941297871289403061486510064429072584259746910423138674192557")
                        );
                    }
                }));
            }
        }).build();


        // create client builder factory
        tcpClientBuilder = new TCPClientBuilder(new RemoteResolver() {
            @Override
            public InetSocketAddress Resolve() throws Exception {
                return new InetSocketAddress("localhost", 5120);
            }
        }).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

                socketChannel.pipeline().addLast(new ProfileHandler());

                socketChannel.pipeline().addLast(new DHClientHandler(createDevice(), new DHKeyResolver() {
                    @Override
                    public DHKey resolver(Device device) throws Exception {
                        return new DHKey(
                                new BigInteger("6849211231874234332173554215962568648211715948614349192108760170867674332076420634857278025209099493881977517436387566623834457627945222750416199306671083"),
                                new BigInteger("13196520348498300509170571968898643110806720751219744788129636326922565480984492185368038375211941297871289403061486510064429072584259746910423138674192557")
                        );
                    }
                }));

                socketChannel.pipeline().addLast(new HeartbeatEchoHandler(5, TimeUnit.SECONDS));

            }
        }).reconnect(2, java.util.concurrent.TimeUnit.SECONDS);
    }

    private Device createDevice() {
        Device device = new Device();

        device.setID(UUID.randomUUID().toString());

        return device;
    }


    @Test
    public void testNoException() throws Exception {

        TCPClient client = tcpClientBuilder.build();

        client.connect();

        client.connected().util();

        client.registerDispatcher((short)1,new DNSListenerDispatcher(new MockDNSListener()));

        DNSResolverRPC resolver = new DNSResolverRPC(client,(short)0);

        resolver.resolve("localhost", 5).util();

        resolver.asyncResolve("hello");

    }

    @Test
    public void testCompatible() throws Exception {
        C0 c0 = new C0();

        c0.setC1(1);
        c0.setC3(3.1415f);

        c0.getC2().setF3("hello world".getBytes());

        BufferWriter writer = new BufferWriter();

        c0.marshal(writer);

        BufferReader reader = new BufferReader(writer.getContent());

        C1 c1 = new C1();

        c1.unmarshal(reader);

        Assert.assertEquals(new String(c1.getC2().getF3()), "hello world");

        Assert.assertEquals(c1.getC1(), 1);

        Assert.assertEquals(0,c1.getC3(), 3.1415f);

        c1.getC2().setF4(new String[]{"hello", "world"});

        c1.setC3(2.0f);

        writer.reset();

        c1.marshal(writer);

        reader.setContent(writer.getContent());

        c0.unmarshal(reader);

        Assert.assertEquals(0f,2.0f,c0.getC3());
    }

    @Test(expected = UnknownException.class)
    public void testUnknownException() throws Exception {

        TCPClient client = tcpClientBuilder.build();

        client.connect();

        client.connected().util();

        DNSResolverRPC resolver = new DNSResolverRPC(client,(short)0);

        resolver.resolve("", 5).util();

    }

    @Test(expected = ResourceException.class)
    public void testResourceException() throws Exception {

        TCPClient client = tcpClientBuilder.build();

        client.connect();

        client.connected().util();

        DNSResolverRPC resolver = new DNSResolverRPC(client,(short)0);

        resolver.resolve("~~~", 5).util();

        client.close();

    }
}
