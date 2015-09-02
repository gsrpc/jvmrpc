package com.gsrpc.test;


import com.gsrpc.Promise;
import com.gsrpc.net.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.net.InetSocketAddress;

@RunWith(BlockJUnit4ClassRunner.class)
public class RPCTest {

    private TCPClient client;

    private TCPServer server;

    private RESTfulRPC rpc;

    @Before
    public void before() throws Exception {

        server = new TCPServerBuilder(new InetSocketAddress("localhost",13512)).Build();

        client = new TCPClientBuilder(new RemoteResolver() {
            @Override
            public InetSocketAddress Resolve() throws Exception {
                return new InetSocketAddress("localhost",13512);
            }
        }).reconnect(2, java.util.concurrent.TimeUnit.SECONDS).Build();


        rpc = new RESTfulRPC(client,(short)0);
    }

    @After
    public void after() throws Exception {
        server.close();
        client.close();
    }


    @Test
    public void testConnect() throws Exception {

        Thread.sleep(4000);

        rpc.Post("hello","world".getBytes("UTF-8"),5).util();

        Assert.assertEquals(new String(rpc.Get("hello", 5).util(),"UTF-8"),"world");

    }
}
