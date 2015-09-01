package com.gsrpc;


public interface Promise<V> {
    void Notify(Exception e,V v);
}
