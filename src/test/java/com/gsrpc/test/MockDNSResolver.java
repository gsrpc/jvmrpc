package com.gsrpc.test;

import java.net.InetSocketAddress;

/**
 * the mock DNSResolver service implement
 */
public class MockDNSResolver implements DNSResolver {


    @Override
    public IPV4 resolve(String domain) throws Exception {
        if("".equals(domain)) {
            throw new UnknownException();
        }

        if("localhost".equals(domain)) {
            IPV4 v4 = new IPV4();

            v4.setAddress(new InetSocketAddress("localhost",1812).getAddress().getAddress());

            return v4;
        }

        throw new ResourceException();
    }

    @Override
    public void asyncResolve(String domain) throws Exception {

    }
}
