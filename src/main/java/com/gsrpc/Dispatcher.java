package com.gsrpc;

public interface Dispatcher {
    Response dispatch(Request request) throws Exception;
}