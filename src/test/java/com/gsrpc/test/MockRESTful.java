package com.gsrpc.test;

import java.util.concurrent.ConcurrentHashMap;


class MockRESTful implements RESTful {

    private final ConcurrentHashMap<String,byte[]> values = new ConcurrentHashMap<String, byte[]>();

    @Override
    public void Post(String name, byte[] content) throws Exception {
        values.put(name,content);
    }

    @Override
    public byte[] Get(String name) throws Exception {
        return values.get(name);
    }
}
