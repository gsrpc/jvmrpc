package com.gsrpc;

public interface Dispatcher {
    Response Dispatch(Request request) throws Exception;
}