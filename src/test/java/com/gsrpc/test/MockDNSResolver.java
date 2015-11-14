package com.gsrpc.test;

import com.gsrpc.Channel;
import com.gsrpc.DispatcherChannel;
import com.gsrpc.SuccessListener;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * the mock DNSResolver service implement
 */
public class MockDNSResolver implements DNSResolver {

    private static final Logger logger = LoggerFactory.getLogger(MockDNSResolver.class);

    private final DNSListenerRPC listener;

    public MockDNSResolver(Channel channel) {

        this.listener = new DNSListenerRPC(channel,(short)1);
    }

    @Override
    public IPV4 resolve(String domain) throws Exception {
        if("".equals(domain)) {
            throw new UnknownException();
        }

        if("localhost".equals(domain)) {
            IPV4 v4 = new IPV4();

            v4.setAddress(new InetSocketAddress("localhost",1812).getAddress().getAddress());

            listener.changed(domain,v4,5);

            return v4;
        }

        throw new ResourceException();
    }

    @Override
    public void asyncResolve(String domain) throws Exception {

    }
}
