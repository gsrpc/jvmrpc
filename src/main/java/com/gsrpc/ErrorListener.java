package com.gsrpc;


public interface ErrorListener<V> {
    void onError(Exception e);
}
