package com.gsrpc.net;


interface Reconnect {

    void disconnected();

    void reconnect() throws Exception;

    void connected();
}
