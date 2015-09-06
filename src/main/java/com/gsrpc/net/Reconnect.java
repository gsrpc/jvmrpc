package com.gsrpc.net;


import java.util.concurrent.TimeUnit;

public interface Reconnect {

    void reconnect(long delay, TimeUnit unit);
}
